package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.advertisement.Advertisement.Type;
import com.devchw.gukmo.user.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    /** 조건에 맞는 메인페이지 광고 5개 조회 */
    public Set<Advertisement> findAdvertisement(LocalDateTime currentDateTime, Type type) {
        return advertisementRepository.findTop5ByStartDateLessThanAndEndDateGreaterThanAndType(currentDateTime, currentDateTime, type);
    }
}
