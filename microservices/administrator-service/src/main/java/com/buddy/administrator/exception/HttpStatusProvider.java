package com.buddy.administrator.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusProvider {	
	HttpStatus getHttpStatus();
}
