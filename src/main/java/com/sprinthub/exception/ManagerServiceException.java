package com.sprinthub.exception;

public class ManagerServiceException extends RuntimeException {

	public ManagerServiceException() {
		super();
	}

	public ManagerServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerServiceException(String message) {
		super(message);
	}
	
}
