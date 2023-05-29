package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.entity.member.Member.Status;
import com.devchw.gukmo.entity.member.Member;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devchw.gukmo.entity.login.QLogin.login;
import static com.devchw.gukmo.entity.member.QMember.member;
import static org.springframework.util.StringUtils.hasText;

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

    /** DataTable MemberList 조회 */
    @Override
    public List<Member> findAllMemberList(int start, int end, DataTableMemberFormDto form) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(form);
        return queryFactory
                .selectFrom(member)
                .leftJoin(member.login, login)
                .fetchJoin()
                .where(
                        userIdContains(form.getUserId()),
                        nicknameContains(form.getNickname()),
                        emailContains(form.getEmail()),
                        statusEq(form.getStatus()),
                        joinDateBetween(form.getStartDate(), form.getEndDate())
                )
                .orderBy(orderSpecifiers)
                .offset(start)
                .limit(end)
                .fetch();
    }

    /** total 조회 */
    public long findAllMemberListTotal(DataTableMemberFormDto form) {
        return queryFactory
                .selectFrom(member)
                .leftJoin(member.login, login)
                .fetchJoin()
                .where(
                        userIdContains(form.getUserId()),
                        nicknameContains(form.getNickname()),
                        emailContains(form.getEmail()),
                        statusEq(form.getStatus()),
                        joinDateBetween(form.getStartDate(), form.getEndDate())
                )
                .fetchCount();
    }


    /**
     * BooleanExpressions
     */
    private BooleanExpression userIdContains(String userId) {
        return hasText(userId)?member.login.userId.contains(userId):null;
    }

    private BooleanExpression nicknameContains(String nickname) {
        return hasText(nickname)?member.nickname.contains(nickname):null;
    }

    private BooleanExpression emailContains(String email) {
        return hasText(email)?member.email.contains(email):null;
    }

    private BooleanExpression statusEq(Status status) {
        return status != null?member.status.eq(status):null;
    }

    private BooleanExpression joinDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate != null && endDate == null) {
            return member.joinDate.goe(startDate);
        } else if(startDate == null && endDate != null) {
            return member.joinDate.lt(endDate);
        } else if(startDate != null && endDate != null) {
            return member.joinDate.goe(startDate).and(member.joinDate.loe(endDate));
        } else {
            return null;
        }
    }

    /** 정렬 */
    private OrderSpecifier[] createOrderSpecifier(DataTableMemberFormDto form) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(form.getSort().equals("nickname")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, member.id));
            }
        } else if(form.getSort().equals("userId")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.login.userId));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, member.login.userId));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            }

        } else if(form.getSort().equals("email")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.email));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, member.email));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            }

        } else if(form.getSort().equals("status")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.status));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, member.status));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
            }
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, member.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
