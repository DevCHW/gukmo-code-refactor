package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.member.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityRepositoryCustom {
    Page<Activity> findAllByMemberId(Long memberId, Pageable pageable);
}
