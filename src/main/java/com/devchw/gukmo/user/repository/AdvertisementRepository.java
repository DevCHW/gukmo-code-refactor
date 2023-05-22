package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;

import static com.devchw.gukmo.entity.advertisement.Advertisement.*;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Set<Advertisement> findTop5ByStartDateLessThanAndEndDateGreaterThanAndType(LocalDateTime startDate, LocalDateTime endDate, Type type);
}
