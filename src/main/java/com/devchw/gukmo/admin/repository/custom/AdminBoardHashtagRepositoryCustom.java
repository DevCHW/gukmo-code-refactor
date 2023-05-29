package com.devchw.gukmo.admin.repository.custom;

import java.util.List;

public interface AdminBoardHashtagRepositoryCustom {

    List<String> findTagNamesByBoardId(Long boardId);
}
