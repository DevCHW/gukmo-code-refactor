package com.devchw.gukmo.user.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportBoardPageDto {
    private String nickname;
    private String subject;
    private Long reportedMemberId;
}
