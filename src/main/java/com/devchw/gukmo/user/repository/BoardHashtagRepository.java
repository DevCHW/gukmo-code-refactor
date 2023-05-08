package com.devchw.gukmo.user.repository;


import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.user.repository.custom.BoardHashtagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, Long> , BoardHashtagRepositoryCustom {

}
