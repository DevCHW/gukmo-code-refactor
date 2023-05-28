package com.devchw.gukmo.admin.dto.api.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityStatsResponse {
    private List<Long> data;
}
