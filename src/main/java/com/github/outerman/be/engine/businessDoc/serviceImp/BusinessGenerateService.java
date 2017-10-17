package com.github.outerman.be.engine.businessDoc.serviceImp;

import com.github.outerman.be.api.dto.FiDocGenetateResultDto;
import com.github.outerman.be.api.ift.IBusinessGenerateService;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.docGenerator.DocTemplateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenxy on 19/7/17.
 *
 * 根据模板, 生成凭证的服务
 */
@Service
public class BusinessGenerateService implements IBusinessGenerateService {
    @Autowired
    private DocTemplateGenerator generator;

    @Override
    public FiDocGenetateResultDto sortConvertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider) {
        return generator.sortConvertVoucher(setOrg, receiptList, templateProvider);
    }

}
