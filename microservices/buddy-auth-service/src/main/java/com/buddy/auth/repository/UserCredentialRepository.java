package com.buddy.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.auth.entity.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID>{
//	findFirstByUser_UserIdOrderByUpdatedOnDesc
	
	Optional<UserCredential> findFirstByUser_UserIdOrderByUpdatedOnDesc(UUID userId);
}
