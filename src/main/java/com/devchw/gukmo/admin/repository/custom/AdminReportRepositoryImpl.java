package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.admin.dto.api.report.DataTableReportFormDto;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.board.QBoard;
import com.devchw.gukmo.entity.comment.QComments;
import com.devchw.gukmo.entity.report.QReport;
import com.devchw.gukmo.entity.report.Report;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devchw.gukmo.entity.advertisement.QAdvertisement.advertisement;
import static com.devchw.gukmo.entity.board.QBoard.board;
import static com.devchw.gukmo.entity.comment.QComments.comments;
import static com.devchw.gukmo.entity.member.QMember.member;
import static com.devchw.gukmo.entity.report.QReport.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
public class AdminReportRepositoryImpl implements AdminReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Report> findAllReportList(int start, int end, DataTableReportFormDto form) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(form);
        return queryFactory
                .selectFrom(report)
                .leftJoin(report.member, member)
                .fetchJoin()
                .leftJoin(report.board, board)
                .fetchJoin()
                .leftJoin(report.comments, comments)
                .fetchJoin()
                .where(
                        idEq(form.getId()),
                        reportTypeEq(form.getReportType()),
                        nicknameContains(form.getNickname()),
                        boardIdEq(form.getBoardId()),
                        commentsIdEq(form.getCommentsId()),
                        simpleReasonEq(form.getSimpleReason()),
                        reportDateBetween(form.getStartDate(), form.getEndDate())
                )
                .orderBy(orderSpecifiers)
                .offset(start)
                .limit(end)
                .fetch();
    }

    @Override
    public long findAllReportListTotal(DataTableReportFormDto form) {
        return queryFactory
                .selectFrom(report)
                .leftJoin(report.member, member)
                .fetchJoin()
                .leftJoin(report.board, board)
                .fetchJoin()
                .leftJoin(report.comments, comments)
                .fetchJoin()
                .where(
                        idEq(form.getId()),
                        reportTypeEq(form.getReportType()),
                        nicknameContains(form.getNickname()),
                        boardIdEq(form.getBoardId()),
                        commentsIdEq(form.getCommentsId()),
                        simpleReasonEq(form.getSimpleReason()),
                        reportDateBetween(form.getStartDate(), form.getEndDate())
                )
                .fetchCount();
    }


    /**
     * BooleanExpressions
     */
    private BooleanExpression idEq(String strId) {
        try {
            Long id = Long.parseLong(strId);
            return id != null?report.id.eq(id):null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private BooleanExpression reportTypeEq(Report.ReportType reportType) {
        return reportType != null?report.type.eq(reportType):null;
    }
    private BooleanExpression nicknameContains(String nickname) {
        return hasText(nickname)?report.member.nickname.contains(nickname):null;
    }
    private BooleanExpression boardIdEq(String strId) {
        try {
            Long id = Long.parseLong(strId);
            return id != null?report.board.id.eq(id):null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BooleanExpression commentsIdEq(String strId) {
        try {
            Long id = Long.parseLong(strId);
            return id != null?report.comments.id.eq(id):null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BooleanExpression simpleReasonEq(String simpleReason) {
        return hasText(simpleReason)?report.simpleReason.eq(simpleReason):null;
    }

    private BooleanExpression reportDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate != null && endDate == null) {
            return report.reportDate.goe(startDate);
        } else if(startDate == null && endDate != null) {
            return report.reportDate.lt(endDate);
        } else if(startDate != null && endDate != null) {
            return report.reportDate.goe(startDate).and(report.reportDate.loe(endDate));
        } else {
            return null;
        }
    }


    /** 정렬 */
    private OrderSpecifier[] createOrderSpecifier(DataTableReportFormDto form) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(form.getSort().equals("id")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.id));
            }
        } else if(form.getSort().equals("reportType")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.type));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.type));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }

        } else if(form.getSort().equals("nickname")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.member.nickname));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.member.nickname));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }

        } else if(form.getSort().equals("boardId")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.board.id));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.board.id));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }
        } else if(form.getSort().equals("commentsId")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.comments.id));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.comments.id));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }
        } else if(form.getSort().equals("simpleReason")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.simpleReason));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.simpleReason));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }
        } else if(form.getSort().equals("reportDate")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.reportDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, report.reportDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
            }
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, report.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
