package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.login.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    public Optional<Login> findByUserId(String userId);
}
