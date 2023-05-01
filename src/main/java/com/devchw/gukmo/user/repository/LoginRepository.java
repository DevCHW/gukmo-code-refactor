package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.login.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUserId(String userId);

    Boolean existsByUserId(String userId);

    @Query("select l.password from Login l where l.id = :id")
    String findPasswordById(@Param("id") Long id);
}
