package com.devchw.gukmo.user.dto.api.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LikeResponse {
    private String deleteOrInsert;
}
