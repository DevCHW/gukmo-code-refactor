package com.devchw.gukmo.admin.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.devchw.gukmo.entity.hashtag.QBoardHashtag.boardHashtag;
import static com.devchw.gukmo.entity.hashtag.QHashtag.hashtag;

@Slf4j
@RequiredArgsConstructor
public class AdminBoardHashtagRepositoryImpl implements AdminBoardHashtagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /** 게시글 번호에 맞는 해시태그 조회 */
    @Override
    public List<String> findTagNamesByBoardId(Long boardId) {
        return queryFactory
                .select(boardHashtag.hashtag.tagName)
                .from(boardHashtag)
                .where(boardHashtag.board.id.eq(boardId))
                .join(boardHashtag.hashtag, hashtag)
                .fetch();
    }
}
