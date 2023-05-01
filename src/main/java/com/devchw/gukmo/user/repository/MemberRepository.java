package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);
}
