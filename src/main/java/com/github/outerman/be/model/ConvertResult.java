package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenxy on 20/7/17.
 *
 * 转换凭证结果
 */
public class ConvertResult implements Serializable {

    private static final long serialVersionUID = 4933038223450135501L;

    /** 转换凭证结果列表 */
    private List<Doc> docList = new ArrayList<>();

    /** 转换凭证失败信息列表 */
    private List<FailDetail> failDetails = new ArrayList<>();

    public void addFail(FailDetail fail) {
        failDetails.add(fail);
    }

    public void addFailed(BusinessVoucher voucher, String msg) {
        FailDetail fail = new FailDetail();
        fail.setVoucher(voucher);
        fail.setMsg(msg);
        addFail(fail);
    }

    /**
     * 获取转换凭证结果列表
     * @return 转换凭证结果列表
     */
    public List<Doc> getDocList() {
        return docList;
    }

    /**
     * 设置转换凭证结果列表
     * @param docList 转换凭证结果列表
     */
    public void setDocList(List<Doc> docList) {
        this.docList = docList;
    }

    /**
     * 获取转换凭证失败信息列表
     * @return 转换凭证失败信息列表
     */
    public List<FailDetail> getFailDetails() {
        return failDetails;
    }

    /**
     * 设置转换凭证失败信息列表
     * @param failDetails 转换凭证失败信息列表
     */
    public void setFailDetails(List<FailDetail> failDetails) {
        this.failDetails = failDetails;
    }

    public static class FailDetail implements Serializable {

        private static final long serialVersionUID = -8129126792162586936L;

        /** 业务单据信息 */
        private BusinessVoucher voucher;

        /** 转换结果 */
        private String msg;

        /**
         * 获取业务单据信息
         * @return 业务单据信息
         */
        public BusinessVoucher getVoucher() {
            return voucher;
        }

        /**
         * 设置业务单据信息
         * @param voucher 业务单据信息
         */
        public void setVoucher(BusinessVoucher voucher) {
            this.voucher = voucher;
        }

        /**
         * 获取转换结果
         * @return 转换结果
         */
        public String getMsg() {
            return msg;
        }

        /**
         * 设置转换结果
         * @param msg 转换结果
         */
        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
