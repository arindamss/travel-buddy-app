package com.buddy.auth.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusProvider {
	HttpStatus getHttpStatus();
}
