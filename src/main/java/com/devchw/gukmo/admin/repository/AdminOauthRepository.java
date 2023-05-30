package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.login.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminOauthRepository extends JpaRepository<Oauth, Long> {
    List<Oauth> findAllByMemberId(Long memberId);
}
