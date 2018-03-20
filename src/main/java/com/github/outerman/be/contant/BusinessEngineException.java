package com.github.outerman.be.contant;

public class BusinessEngineException extends RuntimeException {

    private static final long serialVersionUID = -6729031943659455879L;

    private final String code;
    private Exception innerException = null;
    private Object data;

    public BusinessEngineException(String code, String message) {
        super(message);
        this.code = code;
        this.data = null;
    }

    public BusinessEngineException(String code, String message, Object data, Exception ex) {
        super(message);
        this.code = code;
        this.data = data;
        this.innerException = ex;
    }

    /**
     * 获取innerException
     * 
     * @return innerException
     */
    public Exception getInnerException() {
        return innerException;
    }

    /**
     * 设置innerException
     * 
     * @param innerException
     *            innerException
     */
    public void setInnerException(Exception innerException) {
        this.innerException = innerException;
    }

    /**
     * 获取data
     * 
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置data
     * 
     * @param data
     *            data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取code
     * 
     * @return code
     */
    public String getCode() {
        return code;
    }
}
