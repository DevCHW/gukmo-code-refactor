package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.admin.repository.custom.AdminBoardHashtagRepositoryCustom;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminBoardHashtagRepository extends JpaRepository<BoardHashtag, Long>, AdminBoardHashtagRepositoryCustom {
    @EntityGraph(attributePaths = {"board"})
    List<BoardHashtag> findAllBoardHashtagByBoardId(Long id);
}
