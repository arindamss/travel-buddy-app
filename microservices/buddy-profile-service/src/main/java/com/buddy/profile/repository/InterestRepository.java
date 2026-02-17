package com.buddy.profile.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.profile.entity.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, UUID>{

}
