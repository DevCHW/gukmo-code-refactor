package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.comment.CommentsLike;

import java.util.List;
import java.util.Set;

public interface CommentsLikeRepositoryCustom {
    List<Long> findMyLikeCommentIdsByCommentsIdListAndMemberId(List<Long> commentIds, Long loginMemberId);

}
