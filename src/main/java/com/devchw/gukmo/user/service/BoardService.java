package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.entity.board.Hashtag;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.board.get.BoardListDto;
import com.devchw.gukmo.user.dto.board.post.BoardFormDto;
import com.devchw.gukmo.user.repository.BoardHashtagRepository;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.HashtagRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /** 게시글 번호에 맞는 해시태그 리스트 조회 */
    public List<BoardHashtag> findBoardHashtagByBoardId(Page<BoardListDto> boards) {
        // 게시글들 id값 뽑아서 해시태그 조회하기
        List<Long> boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
        return boardRepository.findBoardHashtagByBoardId(boardIds);
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
}
