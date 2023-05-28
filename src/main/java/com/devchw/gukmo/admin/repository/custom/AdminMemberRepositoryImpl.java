package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.QMember;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.devchw.gukmo.entity.board.QBoard.board;
import static com.devchw.gukmo.entity.member.QActivity.activity;
import static com.devchw.gukmo.entity.member.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class AdminMemberRepositoryImpl implements AdminMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    StringTemplate formattedDate = Expressions.stringTemplate(
            "TO_CHAR({0}, {1})"
            , member.joinDate
            , "yyyy-mm-dd");


    /** 가장 많이 회원가입 한 날의 회원가입 수 조회 쿼리 */
    @Override
    public Long countByJoinMemberMax() {
        return queryFactory
                .select(member.count().as("count"))
                .from(member)
                .groupBy(formattedDate)
                .orderBy(member.count().desc())
                .fetchFirst();
    }

}
