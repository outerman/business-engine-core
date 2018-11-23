package com.github.outerman.be;

import java.util.List;

import com.github.outerman.be.convert.DocConvertor;
import com.github.outerman.be.model.BusinessVoucher;
import com.github.outerman.be.model.ConvertResult;
import com.github.outerman.be.model.Org;
import com.github.outerman.be.template.ITemplateProvider;

/**
 * Created by shenxy on 19/7/17.
 *
 * 业务凭证引擎
 */
public final class BusinessDocEngine {

    /** 数量小数位精度，默认 6 */
    public static int QUANTITY_DECIMAL_SCALE = 6;

    /** 单价小数位精度，默认 6 */
    public static int PRICE_DECIMAL_SCALE = 6;

    /** 金额小数位精度，默认 2 */
    public static int AMOUNT_DECIMAL_SCALE = 2;

    /** 客户档案类型 id */
    public static Long archiveType_customer = 3000160001L;

    /** 供应商档案类型 id */
    public static Long archiveType_supplier = 3000160002L;

    /** 人员档案类型 id */
    public static Long archiveType_person = 3000160003L;

    /** 存货档案类型 id */
    public static Long archiveType_inventory = 3000160005L;

    /** 客户：应收科目 */
    public static Long customer_receivableAccount = 3000150001L;

    /** 客户：预收科目 */
    public static Long customer_receivableInAdvanceAccount = 3000150002L;

    /** 客户：其他应收科目 */
    public static Long customer_otherReceivableAccount = 3000150003L;

    /** 供应商：应付科目 */
    public static Long supplier_payableAccount = 3000150004L;

    /** 供应商：预付科目 */
    public static Long supplier_payableInAdvanceAccount = 3000150005L;

    /** 供应商：其他应付科目 */
    public static Long supplier_otherPayableAccount = 3000150006L;

    /** 人员：其他应收科目 */
    public static Long person_otherReceivableAccount = 3000150007L;

    /** 人员：其他应付科目 */
    public static Long person_otherPayableAccount = 3000150008L;

    /** 存货：存货对应科目 */
    public static Long inventory_inventoryRelatedAccount = 3000150009L;

    /** 存货：销售成本科目 */
    public static Long inventory_salesCostAccount = 3000150010L;

    private static DocConvertor convertor = DocConvertor.getInstance();

    public static ConvertResult convertVoucher(Org org, List<BusinessVoucher> vouchers, ITemplateProvider provider) {
        return convertor.convert(org, vouchers, provider);
    }

    private BusinessDocEngine() {
        // avoid instantiate
    }
}
