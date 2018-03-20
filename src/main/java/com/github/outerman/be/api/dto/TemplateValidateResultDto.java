package com.github.outerman.be.api.dto;

import java.io.Serializable;
import java.util.List;

import com.github.outerman.be.engine.util.StringUtil;

/**
 * 模板校验结果
 * @author gaoxue
 *
 */
public class TemplateValidateResultDto implements Serializable {

    private static final long serialVersionUID = 2034750695585774107L;

    /** 校验结果 */
    private Boolean result;

    /** 凭证模板校验结果 */
    private String docTemplateMessage;

    /** 结算模板校验结果 */
    private String payDocTemplateMessage;

    /** 校验器校验结果 */
    private List<String> validatorMessage;

    private void resetResult() {
        result = StringUtil.isEmpty(docTemplateMessage)
                && StringUtil.isEmpty(payDocTemplateMessage)
                && (validatorMessage == null || validatorMessage.isEmpty());
    }

    public String getErrorMessage() {
        if (result) {
            return "";
        }
        StringBuilder message = new StringBuilder();
        if (!StringUtil.isEmpty(docTemplateMessage)) {
            message.append(docTemplateMessage);
        }
        if (!StringUtil.isEmpty(payDocTemplateMessage)) {
            message.append(payDocTemplateMessage);
        }
        if (validatorMessage != null && !validatorMessage.isEmpty()) {
            message.append(validatorMessage);
        }
        return message.toString();
    }

    /**
     * 获取校验结果
     * @return 校验结果
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * 设置校验结果
     * @param result 校验结果
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * 获取凭证模板校验结果
     * @return 凭证模板校验结果
     */
    public String getDocTemplateMessage() {
        return docTemplateMessage;
    }

    /**
     * 设置凭证模板校验结果
     * @param docTemplateMessage 凭证模板校验结果
     */
    public void setDocTemplateMessage(String docTemplateMessage) {
        this.docTemplateMessage = docTemplateMessage;
        resetResult();
    }

    /**
     * 获取结算模板校验结果
     * @return 结算模板校验结果
     */
    public String getPayDocTemplateMessage() {
        return payDocTemplateMessage;
    }

    /**
     * 设置结算模板校验结果
     * @param payDocTemplateMessage 结算模板校验结果
     */
    public void setPayDocTemplateMessage(String payDocTemplateMessage) {
        this.payDocTemplateMessage = payDocTemplateMessage;
        resetResult();
    }

    /**
     * 获取校验器校验结果
     * @return 校验器校验结果
     */
    public List<String> getValidatorMessage() {
        return validatorMessage;
    }

    /**
     * 设置校验器校验结果
     * @param validatorMessage 校验器校验结果
     */
    public void setValidatorMessage(List<String> validatorMessage) {
        this.validatorMessage = validatorMessage;
        resetResult();
    }

}
