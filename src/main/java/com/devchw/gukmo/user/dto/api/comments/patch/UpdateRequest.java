package com.devchw.gukmo.user.dto.api.comments.patch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class UpdateRequest {
    private String content;
}
