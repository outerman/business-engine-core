package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenxy on 20/7/17.
 *
 * 生成凭证的结果dto
 */
public class FiDocGenetateResultDto implements Serializable {
    private static final long serialVersionUID = 4933038223450135501L;

    private List<FiDocDto> toInsertFiDocList = new ArrayList<>();       //待插入的fiDoc
    private List<FiDocDto> toUpdateFiDocList = new ArrayList<>();       //待更新的fiDoc
    private List<ReceiptResult> failedReceipt = new ArrayList<>();     //数据错误,无法生成的单据(例如"没有业务明细的单据")
    private List<ReceiptResult> unResolvedReceipt = new ArrayList<>(); //无法做生成处理的单据(例如"请会计处理"的单据)

    public void addFailed(ReceiptResult fail) {
        failedReceipt.add(fail);
    }

    public void addFailed(AcmSortReceipt receipt, String msg) {
        ReceiptResult fail = new ReceiptResult();
        fail.setReceipt(receipt);
        fail.setMsg(msg);
        addFailed(fail);
    }

    public List<FiDocDto> getToInsertFiDocList() {
        return toInsertFiDocList;
    }

    public void setToInsertFiDocList(List<FiDocDto> toInsertFiDocList) {
        this.toInsertFiDocList = toInsertFiDocList;
    }

    public List<FiDocDto> getToUpdateFiDocList() {
        return toUpdateFiDocList;
    }

    public void setToUpdateFiDocList(List<FiDocDto> toUpdateFiDocList) {
        this.toUpdateFiDocList = toUpdateFiDocList;
    }

    public List<ReceiptResult> getFailedReceipt() {
        return failedReceipt;
    }

    public void setFailedReceipt(List<ReceiptResult> failedReceipt) {
        this.failedReceipt = failedReceipt;
    }

    public List<ReceiptResult> getUnResolvedReceipt() {
        return unResolvedReceipt;
    }

    public void setUnResolvedReceipt(List<ReceiptResult> unResolvedReceipt) {
        this.unResolvedReceipt = unResolvedReceipt;
    }

    public static class ReceiptResult implements Serializable{
        private static final long serialVersionUID = -8129126792162586936L;

        private AcmSortReceipt receipt;
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public AcmSortReceipt getReceipt() {
            return receipt;
        }

        public void setReceipt(AcmSortReceipt receipt) {
            this.receipt = receipt;
        }
    }
}
