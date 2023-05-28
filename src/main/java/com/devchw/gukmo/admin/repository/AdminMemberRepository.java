package com.devchw.gukmo.admin.repository;


import com.devchw.gukmo.admin.repository.custom.AdminMemberRepositoryCustom;
import com.devchw.gukmo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AdminMemberRepository extends JpaRepository<Member, Long>, AdminMemberRepositoryCustom {
    Long countByJoinDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
