package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.board.get.BoardListDto;
import com.devchw.gukmo.user.dto.board.post.BoardFormDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;
import static org.springframework.util.StringUtils.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

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
        Board board = form.toEntity(writerMember);
        Long id = boardRepository.save(board).getId();

        if(hasText(form.getHashtags())) {   //해시태그가 있다면
            List<String> hashtags = Arrays.asList(form.getHashtags().split(","));
        }
        return id;
    }
}
