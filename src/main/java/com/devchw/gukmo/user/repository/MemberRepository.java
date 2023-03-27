package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.MemberLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * 회원 저장
     */
    public void save(Member member, MemberLogin memberLogin) {
        em.persist(member);
        em.persist(memberLogin);
    }

    /**
     * 회원 단건조회
     */
    public Member findMember(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * 회원 단건조회(닉네임으로)
     */
    public Member findMemberWithNickname(String nickname) {
        return (Member) em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                .setParameter("nickname", nickname);
    }

    /**
     * 회원 리스트 조회
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
