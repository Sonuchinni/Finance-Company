package com.oracle.exception;

public class CustomerNotApprovedException extends RuntimeException {

	public CustomerNotApprovedException(String message) {
        super(message);
    }
}
