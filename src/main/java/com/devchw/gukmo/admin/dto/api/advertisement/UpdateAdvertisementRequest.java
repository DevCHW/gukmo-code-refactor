package com.devchw.gukmo.admin.dto.api.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdvertisementRequest {
    private String startDate;
    private String endDate;
}
