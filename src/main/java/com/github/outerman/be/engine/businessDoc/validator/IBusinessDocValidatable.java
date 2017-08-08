package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;

/**
 * Created by shenxy on 7/7/17.
 *
 * 实现该接口,表示可验证
 */
public interface IBusinessDocValidatable {
    String validate(SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto fiDocDto);
}
