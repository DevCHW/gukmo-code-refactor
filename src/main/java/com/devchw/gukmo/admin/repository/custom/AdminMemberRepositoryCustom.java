package com.devchw.gukmo.admin.repository.custom;

import org.springframework.data.domain.Pageable;

public interface AdminMemberRepositoryCustom {

    public Long countByJoinMemberMax();
}
