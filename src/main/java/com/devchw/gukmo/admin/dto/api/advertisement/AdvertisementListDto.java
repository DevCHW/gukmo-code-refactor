package com.devchw.gukmo.admin.dto.api.advertisement;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.advertisement.Advertisement.Type;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementListDto {
    private Long id;
    private Type type;
    private String startDate;
    private String endDate;

    public AdvertisementListDto toDto(Advertisement advertisement) {
        return AdvertisementListDto.builder()
                .id(advertisement.getId())
                .type(advertisement.getType())
                .startDate(DateUtil.formatLocalDateTimeToString(advertisement.getStartDate()))
                .endDate(DateUtil.formatLocalDateTimeToString(advertisement.getEndDate()))
                .build();
    }
}
