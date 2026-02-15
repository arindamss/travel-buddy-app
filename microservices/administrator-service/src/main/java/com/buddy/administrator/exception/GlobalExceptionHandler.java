package com.buddy.administrator.exception;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyPresentException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyPresentException(UserAlreadyPresentException ex){
		return buildErrorResponse(ex);
	}
	
	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerError ex){
		return buildErrorResponse(ex);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
		return buildErrorResponse(ex);
	}
	
	private <T extends RuntimeException & HttpStatusProvider> ResponseEntity<ErrorResponse> buildErrorResponse(T ex){
		log.error(ex.getMessage() + " -- Status --"+ex.getHttpStatus().value());
		ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ex.getHttpStatus().value())
                .error(ex.getHttpStatus().getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
}
