package com.devchw.gukmo.admin.dto.api.member;

import com.devchw.gukmo.entity.member.Member.Status;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDto {
    private String nickname;
    private String userId;
    private String email;
    private String joinDate;
    private Status status; //REST, ACTIVE, WAIT

    public MemberListDto toDto(Member member) {
        if (member.getLogin() != null) {
            return MemberListDto.builder()
                    .nickname(member.getNickname())
                    .userId(member.getLogin().getUserId())
                    .email(member.getEmail())
                    .joinDate(DateUtil.formatLocalDateTimeToString(member.getJoinDate()))
                    .status(member.getStatus())
                    .build();
        } else {
            return MemberListDto.builder()
                    .nickname(member.getNickname())
                    .userId("없음")
                    .email(member.getEmail())
                    .joinDate(DateUtil.formatLocalDateTimeToString(member.getJoinDate()))
                    .status(member.getStatus())
                    .build();
        }
    }
}
