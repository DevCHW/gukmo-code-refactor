package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.user.dto.board.get.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.devchw.gukmo.entity.board.QAcademy.*;
import static com.devchw.gukmo.entity.board.QBoard.*;
import static com.devchw.gukmo.entity.board.QCurriculum.curriculum;
import static com.devchw.gukmo.entity.board.QNotice.*;
import static com.devchw.gukmo.entity.member.QMember.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /** 커뮤니티 리스트 조회 */
    @Override
    public Page<CommunityListDto> findCommunityList(BoardRequestDto boardRequest, Pageable pageable) {
        List<CommunityListDto> boardList = getCommunityList(boardRequest, pageable);
        long total = getTotal(boardRequest);
        return new PageImpl<>(boardList, pageable, total);
    }

    /** 공지사항 리스트 조회 */
    @Override
    public Page<NoticeListDto> findNoticeList(BoardRequestDto boardRequest, Pageable pageable) {
        List<NoticeListDto> boardList = getNoticeList(boardRequest, pageable);
        long total = getTotal(boardRequest);
        return new PageImpl<>(boardList, pageable, total);
    }

    /** 교육과정 리스트 조회 */
    @Override
    public Page<CurriculumListDto> findCurriculumList(BoardRequestDto boardRequest, Pageable pageable) {
        List<CurriculumListDto> boardList = getCurriculumList(boardRequest, pageable);
        long total = getTotal(boardRequest);
        return new PageImpl<>(boardList, pageable, total);
    }




    /** 국비학원 리스트 조회 */
    @Override
    public Page<AcademyListDto> findAcademyList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(boardRequest);
//        return queryFactory
//                .select(new QAcademyListDto(academy.id,
//                        member.nickname,
//                        academy.firstCategory,
//                        academy.secondCategory,
//                        academy.subject,
//                        academy.content,
//                        academy.writeDate,
//                        academy.views,
//                        member.profileImage,
//                        member.point.as("writerPoint"),
//                        academy.commentCount,
//                        academy.likeCount,
//                        academy.representativeName,
//                        academy.address,
//                        academy.phone,
//                        academy.jurisdiction,
//                        academy.homepage,
//                        academy.academyImage))
//                .from(academy)
//                .where(
//                        firstCategoryEq(boardRequest.getFirstCategory()),
//                        secondCategoryEq(boardRequest.getSecondCategory()),
//                        subjectContainsKeyword(boardRequest.getKeyword())
//                )
//                .join(academy.member, member)   //ManyToOne
//                .orderBy(orderSpecifiers)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
        return null;
    }


    private List<CurriculumListDto> getCurriculumList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(boardRequest);
        return queryFactory
                .select(new QCurriculumListDto(curriculum.id,
                        member.nickname,
                        curriculum.firstCategory,
                        curriculum.secondCategory,
                        curriculum.subject,
                        curriculum.content,
                        curriculum.writeDate,
                        curriculum.views,
                        member.profileImage,
                        member.point.as("writerPoint"),
                        curriculum.commentCount,
                        curriculum.likeCount,
                        curriculum.coreTechnology,
                        curriculum.academyName,
                        curriculum.curriculumStartDate,
                        curriculum.curriculumEndDate,
                        curriculum.recruitmentStartDate,
                        curriculum.recruitmentEndDate,
                        curriculum.recruitsCount,
                        curriculum.url,
                        curriculum.recruitmentPeriod,
                        curriculum.curriculumPeriod))
                .from(curriculum)
                .where(
                        firstCategoryEq(boardRequest.getFirstCategory()),
                        secondCategoryEq(boardRequest.getSecondCategory()),
                        subjectContainsKeyword(boardRequest.getKeyword())
                )
                .join(curriculum.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private List<NoticeListDto> getNoticeList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(boardRequest);
        return queryFactory
                .select(new QNoticeListDto(notice.id,
                        member.nickname,
                        notice.firstCategory,
                        notice.secondCategory,
                        notice.subject,
                        notice.content,
                        notice.writeDate,
                        notice.views,
                        member.profileImage,
                        member.point.as("writerPoint"),
                        notice.commentCount,
                        notice.likeCount,
                        notice.mustRead))
                .from(notice)
                .where(
                        firstCategoryEq(boardRequest.getFirstCategory()),
                        secondCategoryEq(boardRequest.getSecondCategory()),
                        subjectContainsKeyword(boardRequest.getKeyword())
                )
                .join(notice.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    /** 게시물 리스트 조회 */
    private List<CommunityListDto> getCommunityList(BoardRequestDto request, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(request);
        return queryFactory
                .select(new QCommunityListDto(board.id,
                        member.nickname,
                        board.firstCategory,
                        board.secondCategory,
                        board.subject,
                        board.content,
                        board.writeDate,
                        board.views,
                        member.profileImage,
                        member.point.as("writerPoint"),
                        board.commentCount,
                        board.likeCount))
                .from(board)
                .where(
                        firstCategoryEq(request.getFirstCategory()),
                        secondCategoryEq(request.getSecondCategory()),
                        subjectContainsKeyword(request.getKeyword())
                )
                .join(board.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** 조회 게시물 카운트쿼리 */
    private long getTotal(BoardRequestDto request) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .select(board)
                .from(board)
                .where(
                    firstCategoryEq(request.getFirstCategory()),
                    secondCategoryEq(request.getSecondCategory()),
                    subjectContainsKeyword(request.getKeyword())
                )
                .join(board.member, member)
                .fetchCount();
    }

    /**
     * BooleanExpressions
     */
    private BooleanExpression firstCategoryEq(String firstCategory) {
        return hasText(firstCategory) ? board.firstCategory.eq(firstCategory) : null;
    }

    private BooleanExpression secondCategoryEq(String secondCategory) {
        return hasText(secondCategory) ? board.secondCategory.eq(secondCategory) : null;
    }

    private BooleanExpression subjectContainsKeyword(String keyword) {
        return hasText(keyword) ? board.subject.contains(keyword) : null;
    }


    /** 정렬 */
    private OrderSpecifier[] createOrderSpecifier(BoardRequestDto request) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(request.getSort().equals("최신순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.id));
        } else if(request.getSort().equals("추천순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.likeCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.id));
        } else if(request.getSort().equals("댓글순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.commentCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.id));
        } else if(request.getSort().equals("조회순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.views));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.id));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
