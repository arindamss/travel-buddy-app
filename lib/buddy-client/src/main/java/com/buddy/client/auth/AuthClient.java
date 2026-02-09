package com.buddy.client.auth;

import org.springframework.cloud.openfeign.FeignClient;

import com.buddy.auth.client.api.internal.UserInternalApi;

@FeignClient(name = "buddy-auth-service", contextId = "AuthUsetInternalClient")
public interface AuthClient extends UserInternalApi{

}
