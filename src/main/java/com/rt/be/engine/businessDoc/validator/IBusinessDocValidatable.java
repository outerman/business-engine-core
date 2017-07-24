package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.FiDocDto;
import com.rt.be.api.vo.SetOrg;
import com.rt.be.engine.businessDoc.businessTemplate.BusinessTemplate;

/**
 * Created by shenxy on 7/7/17.
 *
 * 实现该接口,表示可验证
 */
public interface IBusinessDocValidatable {
    String validate(SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto fiDocDto);
}
