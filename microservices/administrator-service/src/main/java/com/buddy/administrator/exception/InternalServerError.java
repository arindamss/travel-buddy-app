package com.buddy.administrator.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class InternalServerError extends RuntimeException implements HttpStatusProvider{
	@Getter
	private final HttpStatus httpStatus;
	public InternalServerError(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
