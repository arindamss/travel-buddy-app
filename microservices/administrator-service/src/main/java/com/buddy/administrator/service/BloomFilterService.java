package com.buddy.administrator.service;

public interface BloomFilterService {
	public boolean mightContainUsername(String username);
	
	public void addUsername(String username);
}
