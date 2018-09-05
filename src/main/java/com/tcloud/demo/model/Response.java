package com.tcloud.demo.model;

public class Response {
	String result;

	String message;

	public String getResp() {
		return result;
	}

	public void setResp(String resp) {
		this.result = resp;
	}

	public String getErrorString() {
		return message;
	}

	public void setErrorString(String errorString) {
		this.message = errorString;
	}

	public Response(String resp, String errorString) {
		super();
		this.result = resp;
		this.message = errorString;
	}

}
