package com.buddy.profile.service.impl;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.buddy.profile.service.BloomFilterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BloomFilterServiceImpl implements BloomFilterService{
	
	private final RedissonClient redissonClient;
	
	@Override
	public boolean mightContainUsername(String username) {
		return redissonClient
				.getBloomFilter("bf:usernames")
				.contains(username);
	}

	@Override
	public void addUsername(String username) {
		redissonClient.getBloomFilter("bf:usernames")
						.add(username);
	}

}
