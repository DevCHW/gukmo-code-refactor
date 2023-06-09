package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.entity.board.Academy;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.entity.hashtag.Hashtag;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.board.*;
import com.devchw.gukmo.user.dto.comments.CommentsDto;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.repository.*;
import com.devchw.gukmo.utils.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;
import static com.devchw.gukmo.entity.member.Activity.Division.BOARD_WRITE;
import static com.devchw.gukmo.user.dto.member.ActivityDto.*;
import static com.devchw.gukmo.utils.DateUtil.*;
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
    private final ActivityRepository activityRepository;
    private final FileManager fileManager;

    /** 게시글 번호에 맞는 해시태그 리스트 조회 */
    public List<BoardHashtag> findBoardHashtagByBoardId(List<Long> boardIds) {
        // 게시글들 id값 뽑아서 해시태그 조회하기
        return boardHashtagRepository.findBoardHashtagByBoardIdList(boardIds);
    }

    /** 게시글 상세보기 페이지에 맞춘 단건조회*/
    public BoardDto findBoardById(Long id, HttpSession session) {
        boolean likeExist = false;  //게시글 좋아요 여부
        // 게시물 조회
        Board board = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        // 이전글, 다음글 조회
        String firstCategory = board.getFirstCategory();
        String secondCategory = board.getSecondCategory();

        PrevAndNextBoardDto prevAndNextBoardDto = boardRepository.findPrevAndNextBoardDto(id, firstCategory, secondCategory);

        // 해시태그 조회
        List<String> hashtags = boardHashtagRepository.findTagNamesByBoardId(id);

        // 댓글 조회
        List<Comments> comments = commentsRepository.findCommentListByBoardId(id);
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
        return new BoardDto().toDto(id, likeExist, board, prevAndNextBoardDto, hashtags, commentsDtoList);
    }

    /** 게시물 조회 수 증가 */
    @Transactional
    public void viewCountUp(Long id, HttpServletResponse response, HttpServletRequest request) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("boardView")) {
                    oldCookie = cookie;
                }
            }
        }

        //게시물 조회 수 증가
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                Board board = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
                board.viewCountPlus();
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            Board board = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            board.viewCountPlus();
            Cookie newCookie = new Cookie("boardView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    /** 커뮤니티 게시글 작성 */
    @Transactional
    public Long saveCommunity(CommunityFormDto form) {
        Member writerMember = memberRepository.findById(form.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Board savedBoard = form.toEntity(writerMember);
        Long savedBoardId = boardRepository.save(savedBoard).getId();

        activitySaveAndMemberPointPlus(writerMember, savedBoard);

        //해시태그 저장
        saveHashtag(form.getHashtags(), savedBoard);

        return savedBoardId;
    }

    /** 교육과정 게시물 작성 */
    @Transactional
    public Long saveCurriculum(CurriculumFormDto form) {
        Member writerMember = memberRepository.findById(form.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Curriculum saveCurriculum = form.toEntity(writerMember);
        Board savedBoard = boardRepository.save(saveCurriculum);

        saveHashtag(form.getHashtags(), savedBoard);
        //활동내역 저장, 포인트증가
        activitySaveAndMemberPointPlus(writerMember, savedBoard);
        return savedBoard.getId();
    }

    /** 로그인 여부 알아내기 */
    private boolean isLogin(HttpSession session) {
        return session.getAttribute(SessionConst.LOGIN_MEMBER) != null;
    }


    /** 게시물 삭제 */
    @Transactional
    public void delete(Long id) {
        //활동점수 감소
        Board board = boardRepository.findWithMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        board.getMember().pointMinus(10);

        boardRepository.delete(board);
    }

    /**
     * 글 작성자 닉네임 목록 조회
     */
    public List<WriterNicknameDto> findAllWriterNicknamesByBoardId(List<Long> boardIds) {
        return boardRepository.findAllWriterNicknamesByBoardId(boardIds);
    }

    /** 국비학원 게시글 작성 */
    @Transactional
    public Long saveAcademy(AcademyFormDto form) {
        // 학원 이미지 파일 저장하기.
        String savedFileName = fileManager.save(form.getAcademyImage());

        Member writerMember = memberRepository.findById(form.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Academy saveAcademy = form.toEntity(writerMember, savedFileName);
        Board savedBoard = boardRepository.save(saveAcademy);

        saveHashtag(form.getHashtags(), savedBoard);

        //활동내역 저장, 포인트증가
        activitySaveAndMemberPointPlus(writerMember, savedBoard);
        return savedBoard.getId();
    }


    /** 해시태그 저장 */
    @Transactional
    private void saveHashtag(String hashtags, Board savedBoard) {
        //해시태그가 있다면 해시태그 저장하기
        if(hasText(hashtags)) {
            List<String> tagNames = Arrays.asList(hashtags.split(","));
            for (String tagName : tagNames) {
                Hashtag savedHashtag = hashtagRepository.save(Hashtag.builder()
                        .tagName(tagName)
                        .build());
                BoardHashtag savedBoardHashtag = boardHashtagRepository.save(BoardHashtag.builder()
                        .board(savedBoard)
                        .hashtag(savedHashtag)
                        .build());
                if(savedHashtag == null || savedBoardHashtag == null) throw new BaseException(INTERNAL_SERVER_ERROR);
            }
        }
    }

    /** 국비학원 게시물 수정 */
    @Transactional
    public Long editAcademy(Long id, AcademyFormDto form) {
        Academy academy = (Academy) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        if(!form.getAcademyImage().isEmpty()) { // 사진 수정 요청시
            fileManager.delete(form.getOriginAcademyImage());
            // 새로운 학원 이미지 저장하기
            String savedFileName = fileManager.save(form.getAcademyImage());
            academy.changeAcademyInfo(form.getRepresentativeName(),
                    form.getAddress(),
                    form.getPhone(),
                    form.getHomepage(),
                    savedFileName,
                    form.getSubject(),
                    form.getContent());
        } else {
            academy.changeAcademyInfo(form.getRepresentativeName(),
                    form.getAddress(),
                    form.getPhone(),
                    form.getHomepage(),
                    form.getSubject(),
                    form.getContent());
        }

        if(hasText(form.getHashtags())) {
            editHashtag(id, form.getHashtags(), academy);
        } else {
            List<BoardHashtag> boardHashtags = boardHashtagRepository.findAllBoardHashtagByBoardId(id);
            if(boardHashtags.size() != 0) { //기존 해시태그가 있다면 삭제
                boardHashtags.stream().forEach(bh -> boardHashtagRepository.deleteById(bh.getId()));
            }
        }
        return academy.getId();
    }

    /** 커뮤니티 게시물 수정 */
    @Transactional
    public Long editCommunity(Long id, CommunityFormDto form) {
        Board community = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        community.changeBoardInfo(form.getSubject(), form.getContent());

        if(hasText(form.getHashtags())) {
            editHashtag(id, form.getHashtags(), community);
        } else {
            List<BoardHashtag> boardHashtags = boardHashtagRepository.findAllBoardHashtagByBoardId(id);
            if(boardHashtags.size() != 0) { //기존 해시태그가 있다면 삭제
                boardHashtags.stream().forEach(bh -> boardHashtagRepository.deleteById(bh.getId()));
            }
        }
        return community.getId();
    }

    /** 해시태그 수정 */
    @Transactional
    private void editHashtag(Long id, String strHashtags, Board community) {
        List<String> hashtags = Arrays.asList(strHashtags.split(","));
        List<BoardHashtag> boardHashtags = boardHashtagRepository.findAllBoardHashtagByBoardId(id);
        List<String> originTagNames = boardHashtags.stream().map(bh -> bh.getHashtag().getTagName()).collect(Collectors.toList());
        // 기존 해시태그와 다른지 확인
        if (!hashtags.equals(originTagNames)) {
            if(boardHashtags.size() != 0) {
                boardHashtags.stream().forEach(bh -> boardHashtagRepository.deleteById(bh.getId()));
            }
            saveHashtag(strHashtags, community);
        }
    }


    /** 게시물 작성시 활동점수 올려주기, 활동점수 올려주기 */
    @Transactional
    private void activitySaveAndMemberPointPlus(Member writerMember, Board savedBoard) {

        writerMember.pointPlus(10);

        //활동내역 넣어주기
        Activity activity = Activity.builder()
                .member(writerMember)
                .board(savedBoard)
                .division(BOARD_WRITE)
                .build();

        Activity save = activityRepository.save(activity);
    }

    /** 교육과정 게시글 수정 */
    @Transactional
    public Long editCurriculum(Long id, CurriculumFormDto form) {
        Curriculum curriculum = (Curriculum) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        //수정 메소드 호출
        curriculum.changeCurriculumInfo(
                form.getCoreTechnology(),
                form.getAcademyName(),
                stringToLocalDateTimeConverter(form.getCurriculumStartDate()),
                stringToLocalDateTimeConverter(form.getCurriculumEndDate()),
                stringToLocalDateTimeConverter(form.getRecruitmentStartDate()),
                stringToLocalDateTimeConverter(form.getRecruitmentEndDate()),
                form.getRecruitsCount(),
                form.getUrl(),
                form.getContent(),
                form.getSubject());

        if(hasText(form.getHashtags())) {
            editHashtag(id, form.getHashtags(), curriculum);
        } else {
            List<BoardHashtag> boardHashtags = boardHashtagRepository.findAllBoardHashtagByBoardId(id);
            if(boardHashtags.size() != 0) { //기존 해시태그가 있다면 삭제
                boardHashtags.stream().forEach(bh -> boardHashtagRepository.deleteById(bh.getId()));
            }
        }
        return curriculum.getId();
    }
}
