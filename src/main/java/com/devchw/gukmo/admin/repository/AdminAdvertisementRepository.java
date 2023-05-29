package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.admin.repository.custom.AdminAdvertisementRepositoryCustom;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminAdvertisementRepository extends JpaRepository<Advertisement, Long>, AdminAdvertisementRepositoryCustom {
}
