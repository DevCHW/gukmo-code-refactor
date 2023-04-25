package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.board.QBoardLike;
import com.devchw.gukmo.entity.board.QComments;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.dto.board.QBoardListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devchw.gukmo.entity.board.QBoard.*;
import static com.devchw.gukmo.entity.board.QBoardHashtag.*;
import static com.devchw.gukmo.entity.board.QComments.*;
import static com.devchw.gukmo.entity.member.QMember.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory query;
    @Override
    public Page<BoardListDto> findBoardList(List<String> categories, String keyword, String sort, Pageable pageable) {
        List<BoardListDto> result = query
                .select(new QBoardListDto(board.id,
                        member.nickname,
                        board.firstCategory,
                        board.secondCategory,
                        board.subject,
                        board.content,
                        board.writeDate,
                        board.views,
                        member.profileImage,
                        member.point,
                        board.comments.size(),
                        board.boardHashtags,
                        board.boardLikes.size()))
                .from(board)
                .join(board.member, member)   //ManyToOne
                .leftJoin(board.boardHashtags, boardHashtag) //OneToMany ToMany 두개 이상 fetchJoin 하면 MultipleBagFetchException발생함
                .leftJoin(board.comments, comments) //OneToMany // 또한 반환타입이 Dto일 경우 fetchJoin 불가..
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();
        log.info("게시판 리스트 조회, 게시글 수 ={} 정보 ={}" + result.size(), result);
//        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
        return null;
    }
}
