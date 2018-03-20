package com.github.outerman.be.engine.businessDoc.serviceImp;

import java.util.List;

import com.github.outerman.be.api.dto.FiDocGenetateResultDto;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.docGenerator.DocTemplateGenerator;

/**
 * Created by shenxy on 19/7/17.
 *
 * 根据模板, 生成凭证的服务
 */
public class BusinessGenerateService {

    private static DocTemplateGenerator generator = DocTemplateGenerator.getInstance();

    public static FiDocGenetateResultDto convertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider) {
        return generator.sortConvertVoucher(setOrg, receiptList, templateProvider);
    }

}
