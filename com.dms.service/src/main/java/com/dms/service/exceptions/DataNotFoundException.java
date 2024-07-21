package com.dms.service.exceptions;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Object[] params = null;

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(String message, Object[] params) {
		super(message);
		this.params = params;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}