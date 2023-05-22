package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.user.dto.board.*;
import com.devchw.gukmo.user.dto.board.get.*;
import com.devchw.gukmo.user.dto.member.QActivityDto_WriterNicknameDto;
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
import static com.devchw.gukmo.entity.board.QCurriculum.*;
import static com.devchw.gukmo.entity.board.QNotice.*;
import static com.devchw.gukmo.entity.member.QMember.*;
import static com.devchw.gukmo.user.dto.member.ActivityDto.*;
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
        List<AcademyListDto> boardList = getAcademyList(boardRequest, pageable);
        long total = getTotal(boardRequest);
        return new PageImpl<>(boardList, pageable, total);
    }

    /**
     * 글 작성자 닉네임 목록 조회
     */
    @Override
    public List<WriterNicknameDto> findAllWriterNicknamesByBoardId(List<Long> boardIds) {
        return queryFactory
                .select(new QActivityDto_WriterNicknameDto(
                        board.id.as("id"),
                        board.member.nickname.as("writerNickname")))
                .from(board)
                .where(board.id.in(boardIds))
                .join(board.member, member)   //ManyToOne
                .fetch();
    }


    /** 국비학원 리스트 조회 쿼리 */
    public List<AcademyListDto> getAcademyList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifierForAcademy(boardRequest);
        return queryFactory
                .select(new QAcademyListDto(academy.id,
                        member.nickname,
                        academy.firstCategory,
                        academy.secondCategory,
                        academy.subject,
                        academy.content,
                        academy.writeDate,
                        academy.views,
                        member.profileImage,
                        member.point.as("writerPoint"),
                        academy.commentCount,
                        academy.likeCount,
                        academy.representativeName,
                        academy.address,
                        academy.phone,
                        academy.homepage,
                        academy.academyImage))
                .from(academy)
                .where(
                        firstCategoryEq(boardRequest.getFirstCategory()),
                        secondCategoryEq(boardRequest.getSecondCategory()),
                        subjectContainsKeywordForAcademy(boardRequest.getKeyword())
                )
                .join(academy.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** 교육과정 리스트 조회 쿼리 */
    private List<CurriculumListDto> getCurriculumList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifierForCurriculum(boardRequest);
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
                        secondCategoryEq(boardRequest.getSecondCategory()),
                        subjectContainsKeywordForCurriculum(boardRequest.getKeyword())
                )
                .join(curriculum.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** 공지사항 리스트 조회 쿼리 */
    private List<NoticeListDto> getNoticeList(BoardRequestDto boardRequest, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifierForNotice(boardRequest);
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
                        subjectContainsKeywordForNotice(boardRequest.getKeyword())
                )
                .join(notice.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** 커뮤니티 리스트 조회 쿼리 */
    private List<CommunityListDto> getCommunityList(BoardRequestDto request, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifierForCommunity(request);
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
                        subjectContainsKeywordForCommunity(request.getKeyword())
                )
                .join(board.member, member)   //ManyToOne
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /** 조회 게시물 갯수 조회*/
    private long getTotal(BoardRequestDto request) {
        String firstCategory = request.getFirstCategory();
        String secondCategory = request.getSecondCategory();
        if(hasText(firstCategory)) {
            if(firstCategory.equals("커뮤니티")) {
                return getCommunityTotalCount(request);
            } else if(firstCategory.equals("국비학원")) {
                if(hasText(secondCategory) && secondCategory.equals("교육과정")) {
                    return getCurriculumTotalCount(request);
                } else {
                    return getAcademyTotalCount(request);
                }
            } else if(firstCategory.equals("공지사항")) {
                return getNoticeTotalCount(request);
            }
        }
        return 0;
    }

    private long getCommunityTotalCount(BoardRequestDto request) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .select(board)
                .from(board)
                .where(
                        firstCategoryEq(request.getFirstCategory()),
                        secondCategoryEq(request.getSecondCategory()),
                        subjectContainsKeywordForCommunity(request.getKeyword())
                )
                .join(board.member, member)
                .fetchCount();
    }

    private long getNoticeTotalCount(BoardRequestDto request) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .select(notice)
                .from(notice)
                .where(
                        firstCategoryEq(request.getFirstCategory()),
                        secondCategoryEq(request.getSecondCategory()),
                        subjectContainsKeywordForNotice(request.getKeyword())
                )
                .join(notice.member, member)
                .fetchCount();
    }

    private long getAcademyTotalCount(BoardRequestDto request) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .select(academy)
                .from(academy)
                .where(
                        firstCategoryEq(request.getFirstCategory()),
                        secondCategoryEq(request.getSecondCategory()),
                        subjectContainsKeywordForAcademy(request.getKeyword())
                )
                .join(academy.member, member)
                .fetchCount();
    }

    private long getCurriculumTotalCount(BoardRequestDto request) {
        return queryFactory  //총 갯수 쿼리 따로날리기
                .select(curriculum)
                .from(curriculum)
                .where(
                        secondCategoryEq(request.getSecondCategory()),
                        subjectContainsKeywordForCurriculum(request.getKeyword())
                )
                .join(curriculum.member, member)
                .fetchCount();
    }

    /**
     * BooleanExpressions
     */
    private BooleanExpression firstCategoryEq(String firstCategory) {
        if(hasText(firstCategory)) {
            if(firstCategory.equals("커뮤니티")) {
                return board.firstCategory.eq(firstCategory);
            } else if(firstCategory.equals("국비학원")) {
                return academy.firstCategory.eq(firstCategory);
            } else if(firstCategory.equals("공지사항")) {
                return notice.firstCategory.eq(firstCategory);
            }
        }
        return null;
    }

    private BooleanExpression secondCategoryEq(String secondCategory) {
        if(hasText(secondCategory)) {
            if(secondCategory.equals("교육과정")) {
                return curriculum.secondCategory.eq(secondCategory);
            } else if(secondCategory.equals("QnA") || secondCategory.equals("자유") || secondCategory.equals("스터디") || secondCategory.equals("취미모임") || secondCategory.equals("수강/취업후기")) {
                return board.secondCategory.eq(secondCategory);
            }
        }
        return null;
    }

    private BooleanExpression subjectContainsKeywordForCommunity(String keyword) {
        return hasText(keyword) ? board.subject.contains(keyword) : null;
    }

    private BooleanExpression subjectContainsKeywordForCurriculum(String keyword) {
        return hasText(keyword) ? curriculum.subject.contains(keyword) : null;
    }

    private BooleanExpression subjectContainsKeywordForAcademy(String keyword) {
        return hasText(keyword) ? academy.subject.contains(keyword) : null;
    }

    private BooleanExpression subjectContainsKeywordForNotice(String keyword) {
        return hasText(keyword) ? notice.subject.contains(keyword) : null;
    }

    /** 정렬 */
    private OrderSpecifier[] createOrderSpecifierForCommunity(BoardRequestDto request) {
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

    private OrderSpecifier[] createOrderSpecifierForAcademy(BoardRequestDto request) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(request.getSort().equals("최신순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.id));
        } else if(request.getSort().equals("추천순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.likeCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.id));
        } else if(request.getSort().equals("댓글순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.commentCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.id));
        } else if(request.getSort().equals("조회순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.views));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.id));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, academy.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private OrderSpecifier[] createOrderSpecifierForCurriculum(BoardRequestDto request) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(request.getSort().equals("최신순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.id));
        } else if(request.getSort().equals("추천순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.likeCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.id));
        } else if(request.getSort().equals("댓글순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.commentCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.id));
        } else if(request.getSort().equals("조회순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.views));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.id));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, curriculum.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private OrderSpecifier[] createOrderSpecifierForNotice(BoardRequestDto request) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(request.getSort().equals("최신순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.id));
        } else if(request.getSort().equals("추천순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.likeCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.id));
        } else if(request.getSort().equals("댓글순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.commentCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.id));
        } else if(request.getSort().equals("조회순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.views));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.id));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, notice.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
