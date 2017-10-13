package com.github.outerman.be.engine.util;

import java.util.ArrayList;
import java.util.List;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.vo.SetTaxRateDto;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;

/**
 * 工具类
 * @author gaoxue
 */
public final class CommonUtil {

    static List<Long> simple = new ArrayList<>();
    static List<Long> general = new ArrayList<>();
    static List<Long> special = new ArrayList<>();

    private static void initTaxRate(ITemplateProvider provider) {
        if (!general.isEmpty() && !simple.isEmpty()) {
            return;
        }
        List<SetTaxRateDto> taxRateList = provider.getTaxRateList(0L);
        if (taxRateList == null || taxRateList.isEmpty()) {
            return;
        }
        for (SetTaxRateDto setTaxRateDto : taxRateList) {
            Long id = setTaxRateDto.getId();
            // 1一般 2简易 3其他
            if (setTaxRateDto.getType().equals(1L)) {
                general.add(id);
            }
            if (setTaxRateDto.getType().equals(2L)) {
                simple.add(id);
            }
            if (setTaxRateDto.getType().equals(3L)) {
                special.add(id);
            }
        }
    }

    public static boolean isSimple(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return simple.contains(taxRateId);
    }

    public static boolean isGeneral(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return general.contains(taxRateId);
    }

    public static boolean isSpecial(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return special.contains(taxRateId);
    }

    public static String getIndustryName(Long id) {
        String name;
        if (id == 1L) {
            name = "工业";
        } else if (id == 2L) {
            name = "商贸";
        } else if (id == 3L) {
            name = "服务";
        } else if (id == 4L) {
            name = "信息技术";
        } else {
            name = id.toString();
        }
        return name;
    }

    public static String getAccountingStandardName(Integer id) {
        String name;
        if (id == 18) {
            name = "企业会计准则2007";
        } else if (id == 19) {
            name = "小企业会计准则2013";
        } else {
            name = id.toString();
        }
        return name;
    }

    public static String getInvoiceName(Long id) {
        String name;
        if (id == 200000000000050L) {
            name = "增值税普通发票";
        } else if (id == 200000000000051L) {
            name = "增值税专用发票";
        } else if (id == 200000000000052L) {
            name = "其他票据";
        } else if (id == 200000000000053L) {
            name = "海关进口增值税专用缴款书";
        } else if (id == 200000000000054L) {
            name = "农产品发票";
        } else if (id == 200000000000055L) {
            name = "其他发票";
        } else if (id == 200000000000056L) {
            name = "其他发票(可抵扣)";
        } else if (id == 200000000000057L) {
            name = "未开票";
        } else {
            name = id.toString();
        }
        return name;
    }

    public static String getVatTaxPayerName(Integer id) {
        String name;
        if (id == 41) {
            name = "一般纳税人";
        } else if (id == 42) {
            name = "小规模纳税人";
        } else {
            name = id.toString();
        }
        return name;
    }

    public static String getInventoryPropertyName(Long id) {
        String name;
        if (id.equals(1L)) {
            name = "商品";
        } else if (id.equals(2L)) {
            name = "原材料";
        } else if (id.equals(3L)) {
            name = "半成品";
        } else if (id.equals(4L)) {
            name = "周转材料";
        } else {
            name = id.toString();
        }
        return name;
    }

    public static Long getColumnId(String influence) {
        Long columnId = null;
        if ("assetAttr".equals(influence)) {
            columnId = AcmConst.ASSET_TYPE_COLUMN_ID;
        } else if ("departmentAttr".equals(influence)) {
            columnId = AcmConst.DEPARTMENT_COLUMN_ID;
        } else if ("departmentAttr,personAttr".equals(influence)) {
            columnId = AcmConst.PERSON_COLUMN_ID;
        } else if ("punishmentAttr".equals(influence)) {
            columnId = AcmConst.PENALTY_TYPE_COLUMN_ID;
        } else if ("accountInAttr".equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_TWO_COLUMN_ID;
        } else if ("accountOutAttr".equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_COLUMN_ID;
        } else if ("borrowAttr".equals(influence)) {
            columnId = AcmConst.LOAN_TERM_COLUMN_ID;
        } else if ("inventoryAttr".equals(influence)) {
            columnId = AcmConst.INVENTORY_COLUMN_ID;
        }
        return columnId;
    }

    public static String getInfluenceName(String influence) {
        String name;
        if ("assetAttr".equals(influence)) {
            name = "资产属性";
        } else if ("departmentAttr".equals(influence)) {
            name = "部门属性";
        } else if ("departmentAttr,personAttr".equals(influence)) {
            name = "部门属性、人员属性";
        } else if ("punishmentAttr".equals(influence)) {
            name = "罚款性质";
        } else if ("accountInAttr".equals(influence)) {
            name = "账户属性流入";
        } else if ("accountOutAttr".equals(influence)) {
            name = "账户属性流出";
        } else if ("borrowAttr".equals(influence)) {
            name = "借款期限";
        } else if ("inventoryAttr".equals(influence)) {
            name = "存货属性";
        } else if ("vatTaxpayer".equals(influence)) {
            name = "纳税人";
        } else if ("vatTaxpayer,qualification".equals(influence)) {
            name = "纳税人、认证";
        } else if ("vatTaxpayer,taxType".equals(influence)) {
            name = "纳税人、计税方式";
        } else if ("formula".equals(influence)) {
            name = "表达式";
        } else {
            name = "未知影响因素";
        }
        return name;
    }

    public static String getColumnName(Long id) {
        String name;
        if (id == 1L) {
            name = "金额";
        } else if (id == 2L) {
            name = "价税合计";
        } else if (id == 3L) {
            name = "税额";
        } else if (id == 4L) {
            name = "部门";
        } else if (id == 5L) {
            name = "业务员(人员)";
        } else if (id == 6L) {
            name = "客户(供应商)";
        } else if (id == 7L) {
            name = "商品或服务名称";
        } else if (id == 8L) {
            name = "资产类别";
        } else if (id == 9L) {
            name = "资产名称";
        } else if (id == 10L) {
            name = "数量";
        } else if (id == 11L) {
            name = "单价";
        } else if (id == 12L) {
            name = "结算方式";
        } else if (id == 13L) {
            name = "摘要";
        } else if (id == 14L) {
            name = "银行账号";
        } else if (id == 15L) {
            name = "票据号";
        } else if (id == 16L) {
            name = "税率";
        } else if (id == 17L) {
            name = "抵扣";
        } else if (id == 18L) {
            name = "被投资人";
        } else if (id == 19L) {
            name = "投资类别";
        } else if (id == 20L) {
            name = "投资人";
        } else if (id == 21L) {
            name = "债权人";
        } else if (id == 22L) {
            name = "债务人";
        } else if (id == 23L) {
            name = "罚款性质";
        } else if (id == 24L) {
            name = "借款期限";
        } else if (id == 25L) {
            name = "账号2";
        } else if (id == 26L) {
            name = "项目";
        } else if (id == 27L) {
            name = "票据编码";
        } else if (id == 28L) {
            name = "开票日期";
        } else if (id == 29L) {
            name = "认证";
        } else if (id == 30L) {
            name = "认证月份";
        } else if (id == 31L) {
            name = "可抵扣进项税额";
        } else if (id == 32L) {
            name = "票据类型小规模";
        } else if (id == 33L) {
            name = "票据类型一般";
        } else if (id == 34L) {
            name = "即征即退核算";
        } else {
            name = id.toString();
        }
        return name;
    }
}
