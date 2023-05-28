package com.devchw.gukmo.admin.dto.api.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDto {
    private Long id;
    private String userId;

    public MemberListDto toDto(Member m) {
        return MemberListDto.builder()
                .build();
    }
}
