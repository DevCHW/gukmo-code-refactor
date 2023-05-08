package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.hashtag.BoardHashtag;

import java.util.List;

public interface BoardHashtagRepositoryCustom {
    List<String> findTagNamesByBoardId(Long boardId);

    List<BoardHashtag> findBoardHashtagByBoardIdList(List<Long> boardIdList);
}
