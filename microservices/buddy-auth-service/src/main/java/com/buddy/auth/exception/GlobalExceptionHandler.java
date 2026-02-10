package com.buddy.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
		return buildErrorResponse(ex);
	}
	
	private <T extends RuntimeException &  HttpStatusProvider> ResponseEntity<ErrorResponse> buildErrorResponse(T ex){
		ErrorResponse errorResponse = ErrorResponse.builder()
										.status(ex.getHttpStatus().value())
										.error(ex.getHttpStatus().getReasonPhrase())
										.message(ex.getMessage())
										.build();
		return new ResponseEntity(errorResponse, ex.getHttpStatus());
	}
}
