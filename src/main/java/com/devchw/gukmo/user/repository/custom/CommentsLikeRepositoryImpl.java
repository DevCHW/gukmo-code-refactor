package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.comment.CommentsLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.devchw.gukmo.entity.comment.QCommentsLike.commentsLike;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsLikeRepositoryImpl implements CommentsLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Long> findMyLikeCommentIdsByCommentsIdListAndMemberId(List<Long> commentIds, Long loginMemberId) {
        return queryFactory
                .select(commentsLike.comments.id)
                .from(commentsLike)
                .where(commentsLike.comments.id.in(commentIds)
                .and(commentsLike.member.id.eq(loginMemberId)))
                .fetch();
    }



}
