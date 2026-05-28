package com.buddy.post.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buddy.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{

}
