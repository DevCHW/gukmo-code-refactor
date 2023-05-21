package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.user.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;


    public Page<Activity> findAllByMemberId(Long id, Pageable pageable) {
        return activityRepository.findAllByMemberId(id, pageable);
    }
}
