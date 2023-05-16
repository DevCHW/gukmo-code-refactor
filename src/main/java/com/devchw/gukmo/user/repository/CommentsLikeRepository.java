package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.comment.CommentsLike;
import com.devchw.gukmo.user.repository.custom.CommentsLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Long>, CommentsLikeRepositoryCustom {

    CommentsLike findByCommentsIdAndMemberId(Long commentsId, Long memberId);

    Boolean existsByCommentsIdAndMemberId(Long commentsId, Long memberId);
}
