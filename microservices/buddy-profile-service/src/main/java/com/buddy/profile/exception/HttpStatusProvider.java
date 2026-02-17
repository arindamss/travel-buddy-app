package com.buddy.profile.exception;

import org.springframework.http.HttpStatus;

public interface HttpStatusProvider {	
	HttpStatus getHttpStatus();
}
