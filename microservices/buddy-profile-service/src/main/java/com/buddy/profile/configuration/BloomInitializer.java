package com.buddy.profile.configuration;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BloomInitializer {
	private final RedissonClient redissonClient;
	
	@PostConstruct
	public void init() {
		RBloomFilter<String> usernameFilter = redissonClient.getBloomFilter("bf:usernames");
		usernameFilter.tryInit(1_000_000L, 0.01);
	}
}
