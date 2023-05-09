package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.login.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<Oauth, Long> {

    @Query("select o from Oauth o join fetch o.member where o.id = :oauthId and o.type = :type")
    Optional<Oauth> findByIdAndType(@Param("oauthId") Long oauthId, @Param("type") Oauth.Type type);
}
