package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.penalty.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPenaltyRepository extends JpaRepository<Penalty, Long> {
}
