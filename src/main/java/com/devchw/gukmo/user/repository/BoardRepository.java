package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
