package com.devchw.gukmo.admin.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.devchw.gukmo.entity.member.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class AdminMemberRepositoryImpl implements AdminMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    StringTemplate formattedDateForDay = Expressions.stringTemplate(
            "TO_CHAR({0}, {1})"
            , member.joinDate
            , "yyyy-mm-dd");

    StringTemplate formattedDateForMonth = Expressions.stringTemplate(
            "TO_CHAR({0}, {1})"
            , member.joinDate
            , "yyyy-mm");

    /** 가장 많이 회원가입 한 날의 회원가입 수 조회 쿼리 */
    @Override
    public Long countByJoinMemberMax() {
        return queryFactory
                .select(member.count().as("count"))
                .from(member)
                .groupBy(formattedDateForDay)
                .orderBy(member.count().desc())
                .fetchFirst();
    }
}
