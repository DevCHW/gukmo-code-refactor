package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.user.dto.board.get.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<CommunityListDto> findCommunityList(BoardRequestDto request, Pageable pageable);

    Page<NoticeListDto> findNoticeList(BoardRequestDto boardRequest, Pageable pageable);

    Page<CurriculumListDto> findCurriculumList(BoardRequestDto boardRequest, Pageable pageable);

    Page<AcademyListDto> findAcademyList(BoardRequestDto boardRequest, Pageable pageable);

}
