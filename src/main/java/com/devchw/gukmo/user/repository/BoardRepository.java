package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.repository.custom.CustomBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {
//    @Query("select b from Board b where b.id = :id")
//    Optional<Board> findBoardById(@Param("id") Long id);
}
