package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.entity.hashtag.Hashtag;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.board.get.BoardDto;
import com.devchw.gukmo.user.dto.board.get.CommunityListDto;
import com.devchw.gukmo.user.dto.board.get.PrevAndNextBoardDto;
import com.devchw.gukmo.user.dto.board.post.BoardFormDto;
import com.devchw.gukmo.user.dto.comments.CommentsDto;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.dto.member.WriterDto;
import com.devchw.gukmo.user.repository.*;
import com.devchw.gukmo.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_BOARD;
import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;
import static org.springframework.util.StringUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final CommentsRepository commentsRepository;
    private final CommentsLikeRepository commentsLikeRepository;
    private final BoardLikeRepository boardLikeRepository;

    /** 게시글 번호에 맞는 해시태그 리스트 조회 */
    public List<BoardHashtag> findBoardHashtagByBoardId(List<Long> boardIds) {
        // 게시글들 id값 뽑아서 해시태그 조회하기
        return boardHashtagRepository.findBoardHashtagByBoardIdList(boardIds);
    }

    /** 게시글 작성 */
    @Transactional
    public Long save(BoardFormDto form) {
        Member writerMember = memberRepository.findById(form.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Board savedBoard = form.toEntity(writerMember);
        Long savedBoardId = boardRepository.save(savedBoard).getId();

        //해시태그가 있다면 해시태그 저장하기
        if(hasText(form.getHashtags())) {
            List<String> tagNames = Arrays.asList(form.getHashtags().split(","));
            for (String tagName : tagNames) {
                Hashtag savedHashtag = hashtagRepository.save(Hashtag.builder()
                        .tagName(tagName)
                        .build());
                BoardHashtag savedBoardHashtag = boardHashtagRepository.save(BoardHashtag.builder()
                        .board(savedBoard)
                        .hashtag(savedHashtag)
                        .build());
                if(savedHashtag == null || savedBoardHashtag == null) throw new IllegalStateException("해시태그 저장 실패");
            }
        }
        return savedBoardId;
    }


    /** 게시글 상세보기 페이지에 맞춘 단건조회(추후 광고리스트 조회도 추가 예정)*/
    public BoardDto findBoardById(Long id, HttpSession session) {
        boolean likeExist = false;  //게시글 좋아요 여부

        // 게시물 조회
        Board board = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        // 이전글, 다음글 조회
        String firstCategory = board.getFirstCategory();
        String secondCategory = board.getSecondCategory();

        PrevAndNextBoardDto prevAndNextBoardDto = null;
        if(secondCategory != null) {
            prevAndNextBoardDto = boardRepository.findPrevAndNextBoardDto(id, firstCategory, secondCategory);
        } else {
            prevAndNextBoardDto = boardRepository.findPrevAndNextBoardDto(id, firstCategory);
        }

        // 해시태그 조회
        List<String> hashtags = boardHashtagRepository.findTagNamesByBoardId(id);

        // 현재 광고 리스트 조회(추후 개발)

        // 댓글 조회
        List<Comments> comments = commentsRepository.findAllWithMemberAndParentByBoardIdOrderByParentIdAscNullsFirstCommentIdAsc(id);
        List<CommentsDto> commentsDtoList = null;
        // 로그인중이라면 좋아요 여부 알아오기.
        if(isLogin(session)) { //로그인 중이라면
            LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
            Long loginMemberId = loginMemberDto.getId();
            likeExist = boardLikeRepository.existsByBoardIdAndMemberId(id,loginMemberId);

            if(comments.size() > 0) {   //댓글이 있다면
                // 댓글번호들 뽑기
                List<Long> commentIds = comments.stream().map(c -> c.getId()).collect(Collectors.toList());
                // 좋아요 누른 댓글번호 리스트 뽑기
                List<Long> commentsLikeList = commentsLikeRepository.findMyLikeCommentIdsByCommentsIdListAndMemberId(commentIds, loginMemberId);

                commentsDtoList = CommentsDto.convertHierarchy(comments, commentsLikeList);

            }
        } else {    //로그인중이 아니라면.
            commentsDtoList = CommentsDto.convertHierarchy(comments);
        }

        // 조회된 데이터들을 Dto로 조합
        return createBoardDto(id, likeExist, board, prevAndNextBoardDto, hashtags, commentsDtoList);
    }

    /**
     * BoardDto 조합하기
     */
    private BoardDto createBoardDto(Long id, boolean likeExist, Board board, PrevAndNextBoardDto prevAndNextBoardDto, List<String> hashtags, List<CommentsDto> commentsDtoList) {
        return BoardDto.builder()
                .id(id)
                .subject(board.getSubject())
                .content(board.getContent())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .likeExist(likeExist)
                .commentCount(board.getCommentCount())
                .likeCount(board.getLikeCount())
                .views(board.getViews())
                .writeDate(DateUtil.calculateTime(board.getWriteDate()))
                .comments(commentsDtoList)
                .hashtags(hashtags)
                .prevAndNextBoardDto(prevAndNextBoardDto)
                .writer(WriterDto.toWriterDto(board.getMember()))
                .build();
    }

    /** 로그인 여부 알아내기 */
    private boolean isLogin(HttpSession session) {
        return session.getAttribute(SessionConst.LOGIN_MEMBER) != null;
    }
}
