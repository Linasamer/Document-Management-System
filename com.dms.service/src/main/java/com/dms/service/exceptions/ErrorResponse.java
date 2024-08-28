package com.dms.service.exceptions;

import java.util.Date;


public class ErrorResponse {

	private Date timestamp;
	private int status;
	private String path;
	private String message;
	private String debugMessage;
	private Object[] parameters;

	public ErrorResponse(int status, String path, String message, Object[] parameters) {
		this.timestamp = new Date();
		this.status = status;
		this.message = message;
		this.path = path;
		this.parameters = parameters;
	}

	public ErrorResponse(String errorMessage, int errorCode) {
		this.message = errorMessage;
		this.status = errorCode;
	}

	public ErrorResponse(int status, String path, String message) {
		this.timestamp = new Date();
		this.status = status;
		this.message = message;
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
}
