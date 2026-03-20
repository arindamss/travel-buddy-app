package com.buddy.auth.configuration;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class ClientConfig {
	
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		
		RegisteredClient mobileClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("mobile-client")
				.clientSecret("{noop}secret")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(new AuthorizationGrantType("password"))
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.scope("read")
				.scope("write")
				.build();
		return new InMemoryRegisteredClientRepository(mobileClient);
	}
}
