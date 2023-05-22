package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.user.dto.board.*;
import com.devchw.gukmo.user.dto.member.ActivityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<CommunityListDto> findCommunityList(BoardRequestDto request, Pageable pageable);

    Page<NoticeListDto> findNoticeList(BoardRequestDto boardRequest, Pageable pageable);

    Page<CurriculumListDto> findCurriculumList(BoardRequestDto boardRequest, Pageable pageable);

    Page<AcademyListDto> findAcademyList(BoardRequestDto boardRequest, Pageable pageable);

    List<ActivityDto.WriterNicknameDto> findAllWriterNicknamesByBoardId(List<Long> boardIds);
}
