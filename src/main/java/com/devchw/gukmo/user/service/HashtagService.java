package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.hashtag.Hashtag;
import com.devchw.gukmo.user.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;


    /** 인기 해시태그 조회 */
    public List<String> getTop10BestHashtag() {
        return hashtagRepository.findTop10UsedTags();
    }
}
