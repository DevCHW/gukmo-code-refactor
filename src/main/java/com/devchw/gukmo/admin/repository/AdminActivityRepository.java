package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.member.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminActivityRepository extends JpaRepository<Activity, Long> {
}
