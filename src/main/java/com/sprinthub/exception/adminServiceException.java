package com.sprinthub.exception;

public class adminServiceException extends RuntimeException {

	public adminServiceException() {
		super();
	}

	public adminServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public adminServiceException(String message) {
		super(message);
	}

}
