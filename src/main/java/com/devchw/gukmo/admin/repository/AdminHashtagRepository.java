package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHashtagRepository extends JpaRepository<Hashtag, Long> {
}
