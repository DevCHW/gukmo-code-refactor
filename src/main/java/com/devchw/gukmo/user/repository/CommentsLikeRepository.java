package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.comment.CommentsLike;
import com.devchw.gukmo.user.repository.custom.CommentsLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Long>, CommentsLikeRepositoryCustom {
    Boolean existsByBoardIdAndMemberId(Long commentId, Long memberId);

    Long deleteByBoardIdAndMemberId(Long commentId, Long memberId);
}
