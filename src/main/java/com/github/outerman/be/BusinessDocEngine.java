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

    private static DocConvertor convertor = DocConvertor.getInstance();

    public static ConvertResult convertVoucher(Org org, List<BusinessVoucher> vouchers, ITemplateProvider provider) {
        return convertor.convert(org, vouchers, provider);
    }

    private BusinessDocEngine() {
        // avoid instantiate
    }
}
