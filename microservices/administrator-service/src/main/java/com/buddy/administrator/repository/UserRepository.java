package com.buddy.administrator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.administrator.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	
	public boolean existsByUsername(String username);
}
