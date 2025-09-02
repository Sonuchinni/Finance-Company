package com.oracle.exception;

public class AgentNotFoundException extends RuntimeException{

	public AgentNotFoundException(String message) {
        super(message);
    }
}
