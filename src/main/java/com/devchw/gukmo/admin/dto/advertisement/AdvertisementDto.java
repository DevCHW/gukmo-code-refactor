package com.devchw.gukmo.admin.dto.advertisement;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.advertisement.Advertisement.Type;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    private Long id;
    private Type type;
    private String startDate;
    private String endDate;
    private String fileName;
    private String url;
    private int period;

    /**
     * Entity -> Dto
     */
    public AdvertisementDto toDto(Advertisement advertisement) {
        return AdvertisementDto.builder()
                .id(advertisement.getId())
                .type(advertisement.getType())
                .startDate(DateUtil.formatLocalDateTimeToString(advertisement.getStartDate()))
                .endDate(DateUtil.formatLocalDateTimeToString(advertisement.getEndDate()))
                .fileName(advertisement.getFileName())
                .url(advertisement.getUrl())
                .period(DateUtil.calculateDateGap(advertisement.getStartDate(), advertisement.getEndDate()))
                .build();
    }
}
