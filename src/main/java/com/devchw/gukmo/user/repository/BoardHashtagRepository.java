package com.devchw.gukmo.user.repository;


import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.user.repository.custom.BoardHashtagRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Long> , BoardHashtagRepositoryCustom {

    @EntityGraph(attributePaths = {"board"})
    List<BoardHashtag> findAllBoardHashtagByBoardId(Long id);

//    void deleteAllByBoardId(Long id);
}
