package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.MemberLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    public MemberLogin findByLoginUser(String userId, String password) {
         return em.createQuery("select ml from MemberLogin ml where ml.userId = :userId and ml.password = :password", MemberLogin.class)
                 .setParameter("userId", userId)
                 .setParameter("password", password)
                 .getSingleResult();
    }
}
