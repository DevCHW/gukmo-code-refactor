package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.hashtag.QHashtag;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devchw.gukmo.entity.board.QBoard.board;
import static com.devchw.gukmo.entity.hashtag.QBoardHashtag.boardHashtag;
import static com.devchw.gukmo.entity.hashtag.QHashtag.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardHashtagRepositoryImpl implements BoardHashtagRepositoryCustom {

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

    /** 게시글 번호리스트에 해당하는 해시태그 조회 */
    @Override
    public List<BoardHashtag> findBoardHashtagByBoardIdList(List<Long> boardIds) {
        return queryFactory
                .select(boardHashtag)
                .from(boardHashtag)
                .join(boardHashtag.board, board)
                .where(board.id.in(boardIds))
                .fetch();
    }
}
