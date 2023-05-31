package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.member.AcademyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAcademyMemberRepository extends JpaRepository<AcademyMember, Long> {
}
