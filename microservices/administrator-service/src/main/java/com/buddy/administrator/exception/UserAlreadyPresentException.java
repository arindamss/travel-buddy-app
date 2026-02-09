package com.buddy.administrator.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class UserAlreadyPresentException extends RuntimeException implements HttpStatusProvider{
	@Getter
	private final HttpStatus httpStatus;
	public UserAlreadyPresentException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	
}
