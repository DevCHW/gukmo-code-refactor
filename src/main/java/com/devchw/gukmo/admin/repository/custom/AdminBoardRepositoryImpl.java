package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.entity.board.QBoard;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.devchw.gukmo.entity.board.QBoard.board;
import static com.devchw.gukmo.entity.member.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class AdminBoardRepositoryImpl implements AdminBoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    StringTemplate formattedDate = Expressions.stringTemplate(
            "TO_CHAR({0}, {1})"
            , board.writeDate
            , "yyyy-mm-dd");

    @Override
    public Long countByWriteBoardCountMax() {
        return queryFactory
                .select(board.count().as("count"))
                .from(board)
                .groupBy(formattedDate)
                .orderBy(board.count().desc())
                .fetchFirst();
    }
}
