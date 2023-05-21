package com.devchw.gukmo.user.dto.advertisement;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {
    private String fileName;
    private String url;

    public AdvertisementDto toDto(Advertisement advertisement) {
        return AdvertisementDto.builder()
                .fileName(advertisement.getFileName())
                .url(advertisement.getUrl())
                .build();
    }
}
