package com.buddy.post.service;

import com.buddy.post.entity.MediaMetaData;
import com.buddy.post.repository.MediaMetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetaDataService {

    private final MediaMetaDataRepository mediaMetaDataRepository;

    public MediaMetaData saveMediaMetaData(MediaMetaData mediaMetaData){
        return mediaMetaDataRepository.save(mediaMetaData);
    }


}
