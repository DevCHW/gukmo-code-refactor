package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.dto.board.BoardRequestDto;
import com.devchw.gukmo.user.dto.board.QBoardListDto;
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

import static com.devchw.gukmo.entity.board.QBoard.*;
import static com.devchw.gukmo.entity.board.QBoardHashtag.*;
import static com.devchw.gukmo.entity.member.QMember.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /** 게시판 리스트 조회 */
    @Override
    public Page<BoardListDto> findBoardList(BoardRequestDto request, Pageable pageable) {
        List<BoardListDto> boardList = getBoardList(request, pageable);
        long total = getTotal(request);
        return new PageImpl<>(boardList, pageable, total);
    }

    /** 게시글 번호에 맞는 해시태그 조회 */
    @Override
    public List<BoardHashtag> findBoardHashtagByBoardId(List<Long> boardIds) {
        return queryFactory
                .select(boardHashtag)
                .from(boardHashtag)
                .join(boardHashtag.board, board)
                .where(board.id.in(boardIds))
                .fetch();
    }

    /** 게시물 리스트 조회 */
    private List<BoardListDto> getBoardList(BoardRequestDto request, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(request);
        return queryFactory
                .select(new QBoardListDto(board.id,
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
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.writeDate));
        } else if(request.getSort().equals("추천순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.likeCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.writeDate));
        } else if(request.getSort().equals("댓글순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.commentCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.writeDate));
        } else if(request.getSort().equals("조회순")) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.views));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.writeDate));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
