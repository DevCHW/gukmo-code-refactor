package com.devchw.gukmo.admin.dto.api.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseStatsResponse {
    private List<Long> data;
}
