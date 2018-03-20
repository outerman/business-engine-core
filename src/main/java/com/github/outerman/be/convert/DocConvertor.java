package com.github.outerman.be.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.outerman.be.contant.ErrorCode;
import com.github.outerman.be.model.BusinessVoucher;
import com.github.outerman.be.model.BusinessVoucherDetail;
import com.github.outerman.be.model.BusinessVoucherSettle;
import com.github.outerman.be.model.ConvertResult;
import com.github.outerman.be.model.ConvertResult.FailDetail;
import com.github.outerman.be.model.Doc;
import com.github.outerman.be.model.DocTemplate;
import com.github.outerman.be.model.Account;
import com.github.outerman.be.model.Org;
import com.github.outerman.be.model.SettleTemplate;
import com.github.outerman.be.template.BusinessTemplate;
import com.github.outerman.be.template.ITemplateProvider;
import com.github.outerman.be.template.TemplateManager;
import com.github.outerman.be.util.StringUtil;

/**
 * Created by shenxy on 16/12/28. 生成凭证的工具类
 */
public final class DocConvertor {

    private static DocConvertor instance;

    public static DocConvertor getInstance() {
        if (instance == null) {
            synchronized (DocConvertor.class) {
                if (instance == null) {
                    instance = new DocConvertor();
                }
            }
        }
        return instance;
    }

    private TemplateManager templateManager = TemplateManager.getInstance();

    public ConvertResult convert(Org org, List<BusinessVoucher> vouchers, ITemplateProvider provider) {
        if (vouchers == null || vouchers.isEmpty()) {
            throw ErrorCode.EXCEPTION_VOUCHER_EMPATY;
        }
        if (org == null || org.getId() == null || org.getId() == 0) {
            throw ErrorCode.EXCEPTION_ORG_EMPATY;
        }

        ConvertResult result = new ConvertResult();
        validateVoucher(vouchers, result);
        if (result.getFailDetails().size() == vouchers.size()) {
            return result;
        }

        Map<String, BusinessTemplate> templateMap = new HashMap<>();
        Set<String> accountCodeSet = new HashSet<>();
        List<BusinessVoucherDetail> detailList = new ArrayList<>();
        for (BusinessVoucher voucher : vouchers) {
            if (!voucher.getValid()) {
                continue;
            }
            detailList.addAll(voucher.getDetails());
            for (BusinessVoucherDetail detail : voucher.getDetails()) {
                String businessCode = detail.getBusinessCode();
                if (templateMap.containsKey(businessCode)) {
                    continue;
                }
                BusinessTemplate businessTemplate = templateManager.fetch(org, businessCode, provider);
                templateMap.put(businessCode, businessTemplate);
                accountCodeSet.addAll(businessTemplate.getDocAccountTemplate().getAccountCodeList());
                accountCodeSet.addAll(businessTemplate.getPaymentTemplate().getAccountCodeList());
            }
        }
        Map<String, Account> accountMap = provider.getAccountCode(org.getId(), new ArrayList<String>(accountCodeSet), detailList);

        List<Doc> docList = new ArrayList<>();
        for (BusinessVoucher voucher : vouchers) {
            if (!voucher.getValid()) {
                continue;
            }
            DocHandler docHandler = new DocHandler(org, voucher);
            docHandler.setTemplateMap(templateMap);
            docHandler.setAccountMap(accountMap);
            boolean flag = convertVoucher(docHandler, result);
            if (!flag) {
                continue;
            }

            Doc fiDocDto = docHandler.getDoc();
            docList.add(fiDocDto);
        }

        return result;
    }

    /**
     * 校验单据信息，并将校验失败信息写入 {@code result}，同时单据 {@code valid} 字段赋值 {@code false}
     * <p>单据 {@code valid} 字段赋值 {@code false}，用于之后判断单据信息的有效性，保留原始的 {@code vouchers} 内容以及顺序主要用于异常信息中的第%s条单据
     * @param vouchers
     * @param result
     */
    private void validateVoucher(List<BusinessVoucher> vouchers, ConvertResult result) {
        List<FailDetail> failList = result.getFailDetails();
        for (int index = 0, length = vouchers.size(); index < length; index++) {
            BusinessVoucher voucher = vouchers.get(index);
            if (voucher == null) {
                voucher = new BusinessVoucher();
                vouchers.set(index, voucher);
                voucher.setValid(false);
                FailDetail fail = new FailDetail();
                fail.setMsg(String.format(ErrorCode.VOUCHER_EMPTY, Integer.toString(index + 1)));
                failList.add(fail);
                continue;
            }
            List<BusinessVoucherDetail> detailList = voucher.getDetails();
            if (detailList == null || detailList.isEmpty()) {
                voucher.setValid(false);
                FailDetail fail = new FailDetail();
                fail.setVoucher(voucher);
                fail.setMsg(String.format(ErrorCode.VOUCHER_DETAIL_EMPTY, Integer.toString(index + 1)));
                failList.add(fail);
                continue;
            }
            for (int detailIndex = 0, detailLength = detailList.size(); detailIndex < detailLength; detailIndex++) {
                BusinessVoucherDetail detail = detailList.get(detailIndex);
                if (detail == null) {
                    voucher.setValid(false);
                    FailDetail fail = new FailDetail();
                    fail.setVoucher(voucher);
                    fail.setMsg(String.format(ErrorCode.VOUCHER_DETAIL_NULL, Integer.toString(index + 1),
                            Integer.toString(detailIndex + 1)));
                    failList.add(fail);
                    break;
                }
                String businessCode = detail.getBusinessCode();
                if (StringUtil.isEmpty(businessCode)) {
                    voucher.setValid(false);
                    FailDetail fail = new FailDetail();
                    fail.setVoucher(voucher);
                    fail.setMsg(String.format(ErrorCode.BUSINESS_CODE_EMPTY, Integer.toString(index + 1),
                            Integer.toString(detailIndex + 1)));
                    failList.add(fail);
                    break;
                }
            }
        }
    }

    private boolean convertVoucher(DocHandler docHandler, ConvertResult result) {
        BusinessVoucher voucher = docHandler.getVoucher();
        List<BusinessVoucherDetail> details = reorderDetailList(voucher.getDetails());
        BusinessTemplate businessTemplate = null;
        Map<String, BusinessTemplate> templateMap = docHandler.getTemplateMap();
        // 业务明细转换凭证分录
        for (BusinessVoucherDetail detail : details) {
            String businessCode = detail.getBusinessCode();
            businessTemplate = templateMap.get(businessCode);
            List<DocTemplate> docTemplateList = businessTemplate.getDocAccountTemplate().getDocTemplate(docHandler.getOrg(), detail);
            if (docTemplateList.isEmpty()) {
                result.addFailed(voucher, String.format(ErrorCode.DOC_TEMPLATE_EMPTY, businessCode));
                return false;
            }
            for (DocTemplate docTemplate : docTemplateList) {
                Account account = docHandler.getAccount(docTemplate, detail);
                if (account == null) {
                    result.addFailed(voucher, String.format(ErrorCode.ACCOUNT_CODE_INVALID, docTemplate.getAccountCode()));
                    return false;
                }
                docTemplate.setAccount(account);
                docHandler.addEntry(docTemplate, detail);
            }
        }

        Doc fiDocDto = docHandler.getDoc();
        if (fiDocDto.getEntrys().isEmpty()) {
            // 所有明细处理之后，凭证分录为空：获取到的金额字段都是 0
            result.addFailed(voucher, ErrorCode.ENTRY_EMPTY);
            return false;
        }

        List<BusinessVoucherSettle> settleList = voucher.getSettles();
        if (settleList == null || settleList.isEmpty()) {
            return true;
        }

        // 结算明细转换凭证分录
        for (BusinessVoucherSettle settle : settleList) {
            if (settle == null) {
                continue;
            }

            SettleTemplate payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(settle);
            if (payDocTemplate == null) {
                result.addFailed(voucher, ErrorCode.SETTLE_TEMPLATE_EMPTY);
                return false;
            }
            Account account = docHandler.getAccount(payDocTemplate, settle);
            if (account == null) {
                result.addFailed(voucher, String.format(ErrorCode.ACCOUNT_CODE_INVALID, payDocTemplate.getSubjectDefault()));
                return false;
            }
            payDocTemplate.setAccount(account);

            docHandler.addEntry(payDocTemplate, settle);
        }
        return true;
    }

    /**
     * 收支明细将后续出现的相同业务放在第一次出现位置之后
     * @param details
     * @return 重新排序后的收支明细
     */
    private List<BusinessVoucherDetail> reorderDetailList(List<BusinessVoucherDetail> details) {
        LinkedHashMap<Long, List<BusinessVoucherDetail>> retMap = new LinkedHashMap<>();
        for (BusinessVoucherDetail detail : details) {
            if (detail == null) {
                continue;
            }
            Long businessType = detail.getBusinessType();
            if (retMap.containsKey(businessType)) {
                retMap.get(businessType).add(detail);
            } else {
                List<BusinessVoucherDetail> list = new ArrayList<>();
                list.add(detail);
                retMap.put(businessType, list);
            }
        }

        List<BusinessVoucherDetail> result = new ArrayList<>();
        for (List<BusinessVoucherDetail> list : retMap.values()) {
            result.addAll(list);
        }
        return result;
    }

    private DocConvertor() {
        // avoid instantiate
    }
}
