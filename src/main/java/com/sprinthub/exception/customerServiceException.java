package com.sprinthub.exception;

public class customerServiceException extends RuntimeException {

	public customerServiceException() {
		super();
	}

	public customerServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public customerServiceException(String message) {
		super(message);
	}
	
}
