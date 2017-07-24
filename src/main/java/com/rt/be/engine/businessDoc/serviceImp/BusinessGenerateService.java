package com.rt.be.engine.businessDoc.serviceImp;

import com.rt.be.api.dto.FiDocGenetateResultDto;
import com.rt.be.api.ift.IBusinessGenerateService;
import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.SetOrg;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.businessDoc.docGenerator.DocTemplateGenerator;
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
        return generator.sortConvertVoucher(setOrg, userId, userName, receiptList, templateProvider);
    }

}
