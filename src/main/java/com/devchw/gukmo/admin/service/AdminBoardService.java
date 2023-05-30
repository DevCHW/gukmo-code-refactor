package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.admin.dto.board.BoardListDto;
import com.devchw.gukmo.admin.dto.board.CommunityActivityStatsQueryDto;
import com.devchw.gukmo.admin.dto.board.NoticeFormDto;
import com.devchw.gukmo.admin.repository.AdminBoardHashtagRepository;
import com.devchw.gukmo.admin.repository.AdminBoardRepository;
import com.devchw.gukmo.admin.repository.AdminHashtagRepository;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Notice;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.entity.hashtag.Hashtag;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBoardService {
    private final AdminBoardRepository adminBoardRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminHashtagRepository adminHashtagRepository;
    private final AdminBoardHashtagRepository adminBoardHashtagRepository;

    /** 오늘자 커뮤니티 활성비율 통계 얻기 */
    public List<Long> findCommunityActivityStats() {
        CommunityActivityStatsQueryDto findData = adminBoardRepository.findCommunityActivityStats();
        List<Long> data = new ArrayList<>();
        data.add(findData.getFreePercentage());
        data.add(findData.getQnaPercentage());
        data.add(findData.getStudyPercentage());
        data.add(findData.getHobbyPercentage());
        data.add(findData.getReviewPercentage());
        return data;
    }

    /** 공지사항 작성 */
    @Transactional
    public Long saveNotice(NoticeFormDto form) {
        Member writerMember = adminMemberRepository.findById(form.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Notice savedBoard = form.toEntity(writerMember);
        Long savedBoardId = adminBoardRepository.save(savedBoard).getId();

        //해시태그 저장
        saveHashtag(form.getHashtags(), savedBoard);

        return savedBoardId;
    }

    /** 공지사항 수정 */
    @Transactional
    public Long editNotice(Long id, NoticeFormDto form) {
        Notice notice = (Notice) adminBoardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        notice.changeBoardInfo(form.getSubject(), form.getContent(), form.getMustRead());

        if(hasText(form.getHashtags())) {
            editHashtag(id, form.getHashtags(), notice);
        } else {
            List<BoardHashtag> boardHashtags = adminBoardHashtagRepository.findAllBoardHashtagByBoardId(id);
            if(boardHashtags.size() != 0) { //기존 해시태그가 있다면 삭제
                boardHashtags.stream().forEach(bh -> adminBoardHashtagRepository.deleteById(bh.getId()));
            }
        }
        return notice.getId();
    }

    /** 해시태그 수정 */
    private void editHashtag(Long id, String strHashtags, Notice notice) {
        List<String> hashtags = Arrays.asList(strHashtags.split(","));
        List<BoardHashtag> boardHashtags = adminBoardHashtagRepository.findAllBoardHashtagByBoardId(id);
        List<String> originTagNames = boardHashtags.stream().map(bh -> bh.getHashtag().getTagName()).collect(Collectors.toList());
        // 기존 해시태그와 다른지 확인
        if (!hashtags.equals(originTagNames)) {
            if(boardHashtags.size() != 0) {
                boardHashtags.stream().forEach(bh -> adminBoardHashtagRepository.deleteById(bh.getId()));
            }
            saveHashtag(strHashtags, notice);
        }
    }

    /** 해시태그 저장 */
    @Transactional
    private void saveHashtag(String hashtags, Notice savedNotice) {
        //해시태그가 있다면 해시태그 저장하기
        if(hasText(hashtags)) {
            List<String> tagNames = Arrays.asList(hashtags.split(","));
            for (String tagName : tagNames) {
                Hashtag savedHashtag = adminHashtagRepository.save(Hashtag.builder()
                        .tagName(tagName)
                        .build());
                BoardHashtag savedBoardHashtag = adminBoardHashtagRepository.save(BoardHashtag.builder()
                        .board(savedNotice)
                        .hashtag(savedHashtag)
                        .build());
                if(savedHashtag == null || savedBoardHashtag == null) throw new BaseException(INTERNAL_SERVER_ERROR);
            }
        }
    }


    /** Datatable boardList 조회 */
    public List<BoardListDto> findAllBoardList(int start, int length, MultiValueMap<String, String> formData, Long id) {
        int end = length;

        List<Board> findBoardList = adminBoardRepository.findAllByMemberId(id, start, end);
        List<BoardListDto> data = findBoardList.stream().map(b -> new BoardListDto().toDto(b)).collect(Collectors.toList());
        return data;
    }

    public long findAllBoardListTotal(Long memberId) {
        return adminBoardRepository.countByMemberId(memberId);
    }
}
