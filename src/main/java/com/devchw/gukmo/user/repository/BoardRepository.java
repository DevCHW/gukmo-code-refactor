package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.dto.board.get.BoardRequestDto;
import com.devchw.gukmo.user.dto.board.get.NoticeListDto;
import com.devchw.gukmo.user.dto.board.get.PrevAndNextBoardDto;
import com.devchw.gukmo.user.repository.custom.BoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("select b from Board b join b.member where b.id = :id")
    Optional<Board> findById(@Param("id") Long id);

    /**
     * 오라클 네이티브 쿼리 이전글, 다음글 secondCategory가 있을 때
     */
    @Query(value = " select previousId, previousSubject, nextId, nextSubject " +
                   " from " +
                   " ( " +
                   "    select board_id, " +
                   "           lag(board_id, 1, 0) over(order by board_id desc) as previousId, " +
                   "           lag(subject, 1, '이전글이 없습니다.') over (order by board_id desc) as previousSubject," +
                   "           lead(board_id, 1, 0) over(order by board_id desc) as nextId, "+
                   "           lead(subject, 1, '다음글이 없습니다.') over (order by board_id desc) as nextSubject " +
                   "    from (select board_id,subject from board " +
                   "          where first_category = :firstCategory and second_category = :secondCategory) A" +
                   " ) " +
                   " where board_id = :boardId",
            nativeQuery = true)
    PrevAndNextBoardDto findPrevAndNextBoardDto(@Param("boardId") Long boardId,
                                                @Param("firstCategory") String firstCategory,
                                                @Param("secondCategory") String secondCategory);


    /**
     * 오라클 네이티브 쿼리 이전글, 다음글 secondCategory가 없을 때
     */
    @Query(value = " select previousId, previousSubject, nextId, nextSubject " +
                   " from " +
                   " ( " +
                   "    select board_id, " +
                   "           lag(board_id, 1, 0) over(order by board_id desc) as previousId, " +
                   "           lag(subject, 1, '이전글이 없습니다.') over (order by board_id desc) as previousSubject," +
                   "           lead(board_id, 1, 0) over(order by board_id desc) as nextId, "+
                   "           lead(subject, 1, '다음글이 없습니다.') over (order by board_id desc) as nextSubject " +
                   "    from (select board_id,subject from board " +
                   "          where first_category = :firstCategory) A" +
                   " ) " +
                   " where board_id = :boardId",
            nativeQuery = true)
    PrevAndNextBoardDto findPrevAndNextBoardDto(@Param("boardId") Long boardId,
                                                @Param("firstCategory") String firstCategory);


    @EntityGraph(attributePaths = {"member"})
    List<Board> findTop5BoardBySecondCategoryOrderByIdDesc(String secondCategory);

}
