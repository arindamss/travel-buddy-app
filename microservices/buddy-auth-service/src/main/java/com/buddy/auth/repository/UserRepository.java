package com.buddy.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.auth.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	
	List<User> findByUsername(String username);
}
