package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterDto {
    private Long id;
    private String profileImage;
    private String nickname;
    private Long point;

    public static WriterDto toWriterDto(Member member) {
        return WriterDto.builder()
                .id(member.getId())
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .build();
    }
}
