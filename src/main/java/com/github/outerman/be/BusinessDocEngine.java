package com.github.outerman.be;

import java.util.List;

import com.github.outerman.be.convert.DocConvertor;
import com.github.outerman.be.model.AcmSortReceipt;
import com.github.outerman.be.model.FiDocGenetateResultDto;
import com.github.outerman.be.model.SetOrg;
import com.github.outerman.be.template.ITemplateProvider;

/**
 * Created by shenxy on 19/7/17.
 *
 * 业务凭证引擎
 */
public class BusinessDocEngine {

    private static DocConvertor convertor = DocConvertor.getInstance();

    public static FiDocGenetateResultDto convertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider) {
        return convertor.convert(setOrg, receiptList, templateProvider);
    }

}
