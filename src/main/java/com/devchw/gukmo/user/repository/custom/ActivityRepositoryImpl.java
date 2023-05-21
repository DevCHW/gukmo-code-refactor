package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.QActivity;
import com.devchw.gukmo.entity.member.QMember;
import com.devchw.gukmo.user.dto.board.get.BoardRequestDto;
import com.devchw.gukmo.user.dto.board.get.CommunityListDto;
import com.devchw.gukmo.user.dto.board.get.QAcademyListDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devchw.gukmo.entity.board.QAcademy.academy;
import static com.devchw.gukmo.entity.board.QBoard.board;
import static com.devchw.gukmo.entity.member.QActivity.activity;
import static com.devchw.gukmo.entity.member.QMember.*;
import static com.devchw.gukmo.entity.member.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /** 회원 활동내역 조회 */
    @Override
    public Page<Activity> findAllByMemberId(Long memberId, Pageable pageable) {
        List<Activity> activities = getActivityMemberList(memberId, pageable);
        long total = getActivityMemberTotalCount(memberId);
        return new PageImpl<>(activities, pageable, total);
    }

    /** 회원 활동내역 조회 쿼리 */
    private List<Activity> getActivityMemberList(Long memberId, Pageable pageable) {
        return queryFactory
                .select(activity)
                .from(activity)
                .join(activity.member, member).fetchJoin()
                .where(activity.member.id.eq(memberId))
                .leftJoin(activity.board, board).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(activity.activityDate.desc())
                .fetch();
    }

    /** 회원 활동내역 카운트 쿼리 */
    private long getActivityMemberTotalCount(Long memberId) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .selectFrom(activity)
                .join(activity.member, member)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetchCount();
    }
}
