package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"login"})
    Optional<Member> findMemberById(Long id);

    Optional<Member> findByLoginId(Long id);
}
