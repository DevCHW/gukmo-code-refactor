package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.login.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<Oauth, Long> {

    @Query("select o from Oauth o where o.authId = :authId and o.type = :type")
    Optional<Oauth> findByAuthIdAndType(@Param("authId") String authId, @Param("type") Oauth.Type type);
}
