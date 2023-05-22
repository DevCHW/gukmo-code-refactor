package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query("SELECT h.tagName FROM Hashtag h GROUP BY h.tagName ORDER BY COUNT(h.tagName) DESC")
    List<String> findTop10UsedTags();
}
