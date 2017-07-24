package com.rt.be.api.constant;

public class BusinessEngineException extends RuntimeException {

	private static final long serialVersionUID = -6729031943659455879L;
	private final String code;
	private Exception innerException = null;
	private Object data;
	private Type type;

	public enum Type {
		error,		//对应于前端的红叉提示
		warning		//对应于前端的黄叹号提示
	}

	public BusinessEngineException(String code, String message) {
		super(message);
		this.code = code;
		this.data = null;
	}

	public BusinessEngineException(String code, String message, Type type) {
		super(message);
		this.code = code;
		this.data = null;
		this.type = type;
	}

	public BusinessEngineException(String code, String message, Object data, Exception ex) {

        super(message);
		this.code = code; 
		this.data = data;
		this.innerException = ex;
	}
}
