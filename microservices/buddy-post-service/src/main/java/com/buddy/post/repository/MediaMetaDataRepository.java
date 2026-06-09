package com.buddy.post.repository;

import com.buddy.post.entity.MediaMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaMetaDataRepository extends JpaRepository<MediaMetaData, UUID> {
}
