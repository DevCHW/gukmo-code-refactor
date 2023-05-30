package com.devchw.gukmo.admin.dto.api.advertisement;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.devchw.gukmo.entity.advertisement.Advertisement.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementFormRequest {
    private String type;
    private String url;
    private String startDate;
    private String endDate;
    public Advertisement toEntity(String saveFileName) {
        Type type = this.type.equals("BOARD") ? Type.BOARD : Type.MAIN;
        return Advertisement.builder()
                .type(type)
                .url(url)
                .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                .endDate(DateUtil.stringToLocalDateTimeConverter(endDate))
                .fileName(saveFileName)
                .build();
    }
}
