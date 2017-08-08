package com.github.outerman.be.api.ift;

import com.github.outerman.be.api.dto.FiDocGenetateResultDto;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;

import java.util.List;

/**
 * Created by shenxy on 21/7/17.
 *
 * 凭证生成服务
 */
public interface IBusinessGenerateService {

    FiDocGenetateResultDto sortConvertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider);

}
