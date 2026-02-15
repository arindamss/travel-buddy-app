package com.buddy.administrator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.administrator.entity.UserInterest;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, UUID>{

}
