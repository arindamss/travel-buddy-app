package com.buddy.post.client.dto.request;

import java.util.List;

import com.buddy.post.client.dto.enums.Visibility;

import lombok.Data;

@Data
public class CreatePostRequest {

    private String content;

    private Visibility visibility;

    private List<PostMediaRequest> media;
}
