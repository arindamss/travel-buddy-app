package com.buddy.auth.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException implements HttpStatusProvider {
	
	private HttpStatus httpStatus;
	
	public AuthenticationException(String msg, HttpStatus httpStatus){
		super(msg);
		this.httpStatus = httpStatus;
	}
	
	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

}
