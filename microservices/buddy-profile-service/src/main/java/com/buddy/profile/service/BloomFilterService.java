package com.buddy.profile.service;

public interface BloomFilterService {
	public boolean mightContainUsername(String username);
	
	public void addUsername(String username);
}
