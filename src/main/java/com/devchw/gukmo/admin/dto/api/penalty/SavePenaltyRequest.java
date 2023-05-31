package com.devchw.gukmo.admin.dto.api.penalty;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.penalty.Penalty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePenaltyRequest {
    private Long memberId;
    private String simpleReason;
    @Builder.Default
    private String detailReason = "";
    private String period;

    /**
     * Dto -> Entity
     */
    public Penalty toEntity(Member member) {
        int period = Integer.parseInt(this.period.replace("일", ""));
        return Penalty.builder()
                .member(member)
                .simpleReason(simpleReason)
                .detailReason(detailReason)
                .period(period)
                .build();
    }
}
