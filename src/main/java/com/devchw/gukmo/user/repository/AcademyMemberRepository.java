package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.AcademyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyMemberRepository extends JpaRepository<AcademyMember, Long> {

    Boolean existsByAcademyName(String academyName);
}
