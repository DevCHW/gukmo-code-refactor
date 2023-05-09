package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.comment.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByBoardId(Long id);

    @Query("select c from Comments c join c.member left join fetch c.parent where c.board.id = :boardId order by c.parent.id asc nulls first, c.id asc")
    List<Comments> findAllWithMemberAndParentByBoardIdOrderByParentIdAscNullsFirstCommentIdAsc(@Param("boardId") Long boardId);
}