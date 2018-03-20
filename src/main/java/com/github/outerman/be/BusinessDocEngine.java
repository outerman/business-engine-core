package com.github.outerman.be;

import java.util.List;

import com.github.outerman.be.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.businessDoc.generator.DocTemplateGenerator;
import com.github.outerman.be.model.AcmSortReceipt;
import com.github.outerman.be.model.SetOrg;

/**
 * Created by shenxy on 19/7/17.
 *
 * 根据模板, 生成凭证的服务
 */
public class BusinessDocEngine {

    private static DocTemplateGenerator generator = DocTemplateGenerator.getInstance();

    public static FiDocGenetateResultDto convertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider) {
        return generator.sortConvertVoucher(setOrg, receiptList, templateProvider);
    }

}
