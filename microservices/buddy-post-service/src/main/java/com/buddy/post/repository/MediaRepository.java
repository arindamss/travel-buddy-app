package com.buddy.post.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buddy.post.entity.Media;

public interface MediaRepository extends JpaRepository<Media, UUID> {

}
