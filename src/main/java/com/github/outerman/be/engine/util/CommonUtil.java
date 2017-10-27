package com.github.outerman.be.engine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static List<Column> columns = new ArrayList<>();

    static {
        columns.add(new Column(1L, "金额", "amount"));
        columns.add(new Column(2L, "价税合计", "taxInclusiveAmount"));
        columns.add(new Column(3L, "税额", "tax"));
        columns.add(new Column(4L, "部门", "department"));
        columns.add(new Column(5L, "业务员(人员)", "employee"));
        columns.add(new Column(6L, "客户(供应商)", "consumer|vendor"));
        columns.add(new Column(7L, "商品或服务名称", "inventory"));
        columns.add(new Column(8L, "资产类别", "assetType"));
        columns.add(new Column(9L, "资产名称", "assetId"));
        columns.add(new Column(10L, "数量", "commodifyNum"));
        columns.add(new Column(11L, "单价", "price"));
        columns.add(new Column(12L, "结算方式", "settleStyle"));
        columns.add(new Column(13L, "摘要", "memo"));
        columns.add(new Column(14L, "银行账号", "bankAccountId"));
        columns.add(new Column(15L, "票据号", "notesNum"));
        columns.add(new Column(16L, "税率", "taxRateId"));
        columns.add(new Column(17L, "抵扣", "isDeduction"));
        columns.add(new Column(18L, "被投资人", "byInvestorId"));
        columns.add(new Column(19L, "投资类别", "")); // 投资类别没有对应字段
        columns.add(new Column(20L, "投资人", "investorId"));
        columns.add(new Column(21L, "债权人", "creditor"));
        columns.add(new Column(22L, "债务人", "debtor"));
        columns.add(new Column(23L, "罚款性质", "penaltyType"));
        columns.add(new Column(24L, "借款期限", "loanTerm"));
        columns.add(new Column(25L, "账号2", "inBankAccountId"));
        columns.add(new Column(26L, "项目", "project"));
        columns.add(new Column(27L, "票据编码", "invoiceCode"));
        columns.add(new Column(28L, "开票日期", "billingDate"));
        columns.add(new Column(29L, "认证", "isQualification"));
        columns.add(new Column(30L, "认证月份", "certificationMonth"));
        columns.add(new Column(31L, "可抵扣进项税额", "deductibleInputTax"));
        columns.add(new Column(34L, "即征即退核算", "drawbackPolicy"));
        columns.add(new Column(35L, "备注", "memo"));
        columns.add(new Column(1000L, "数值扩展字段0", "ext0"));
        columns.add(new Column(1001L, "数值扩展字段1", "ext1"));
        columns.add(new Column(1002L, "数值扩展字段2", "ext2"));
        columns.add(new Column(1003L, "数值扩展字段3", "ext3"));
        columns.add(new Column(1004L, "数值扩展字段4", "ext4"));
        columns.add(new Column(1005L, "数值扩展字段5", "ext5"));
        columns.add(new Column(1006L, "数值扩展字段6", "ext6"));
        columns.add(new Column(1007L, "数值扩展字段7", "ext7"));
        columns.add(new Column(1008L, "数值扩展字段8", "ext8"));
        columns.add(new Column(1009L, "数值扩展字段9", "ext9"));
    }

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
        if (AcmConst.INFLUENCE_DEPARTMENT_ATTR.equals(influence)) {
            columnId = AcmConst.DEPARTMENT_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_DEPT_PERSON_ATTR.equals(influence)) {
            columnId = AcmConst.PERSON_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_VAT_TAXPAYER.equals(influence)) {

        } else if (AcmConst.INFLUENCE_VAT_TAXPAYER_QUALIFICATION.equals(influence)) {
            columnId = AcmConst.QUALIFICATION_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_VAT_TAXPAYER_TAXTYPE.equals(influence)) {
            columnId = AcmConst.TAX_RATE_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_PUNISHMENT_ATTR.equals(influence)) {
            columnId = AcmConst.PENALTY_TYPE_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_BORROW_ATTR.equals(influence)) {
            columnId = AcmConst.LOAN_TERM_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_INVENTORY_ATTR.equals(influence)) {
            columnId = AcmConst.INVENTORY_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_ASSET_ATTR.equals(influence)) {
            columnId = AcmConst.ASSET_TYPE_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_ACCOUNTIN_ATTR.equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_TWO_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_ACCOUNTOUT_ATTR.equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_COLUMN_ID;
        } else if (AcmConst.INFLUENCE_FORMULA.equals(influence)) {

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

    public static String getColumnNameById(Long id) {
        String name = null;
        if (id == null) {
            return name;
        }
        for (Column column : columns) {
            if (id.equals(column.getId())) {
                name = column.getName();
                break;
            }
        }
        return name;
    }

    public static String getColumnNameByFieldName(String fieldName) {
        String name = null;
        if (StringUtil.isEmpty(fieldName)) {
            return name;
        }
        for (Column column : columns) {
            if (fieldName.equals(column.getFieldName())) {
                name = column.getName();
                break;
            }
        }
        return name;
    }

    public static Long getColumnIdByName(String name) {
        Long id = null;
        if (StringUtil.isEmpty(name)) {
            return id;
        }
        for (Column column : columns) {
            if (name.equals(column.getName())) {
                id = column.getId();
                break;
            }
        }
        return id;
    }

    public static Long getColumnIdByFieldName(String fieldName) {
        Long id = null;
        if (StringUtil.isEmpty(fieldName)) {
            return id;
        }
        for (Column column : columns) {
            if (fieldName.equals(column.getFieldName())) {
                id = column.getId();
                break;
            }
        }
        return id;
    }

    public static Map<String, String> getFieldName2NameMap() {
        Map<String, String> map = new HashMap<>();
        for (Column column : columns) {
            map.put(column.getFieldName(), column.getName());
        }
        return map;
    }

    public static class Column {

        private Long id;

        private String name;

        private String fieldName;

        public Column(Long id, String name, String fieldName) {
            this.id = id;
            this.name = name;
            this.fieldName = fieldName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }

}
