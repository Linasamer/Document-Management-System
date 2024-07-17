package com.dms.service.exceptions;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Object[] params = null;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Object[] params) {
		super(message);
		this.params = params;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, Object[] params, Throwable cause) {
		super(message, cause);
		this.params = params;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
