package com.buddy.administrator.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException implements HttpStatusProvider{
	
	private HttpStatus httpStatus;
	
	public UserNotFoundException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

}
