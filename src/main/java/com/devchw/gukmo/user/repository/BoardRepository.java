package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
