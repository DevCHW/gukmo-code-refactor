package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.entity.member.Member;
import java.util.List;
public interface AdminMemberRepositoryCustom {

    Long countByJoinMemberMax();

    List<Member> findAllMemberList(int start, int end, DataTableMemberFormDto form);

    long findAllMemberListTotal(DataTableMemberFormDto form);
}
