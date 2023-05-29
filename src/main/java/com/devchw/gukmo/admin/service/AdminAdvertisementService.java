package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.admin.repository.AdminAdvertisementRepository;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAdvertisementService {
    private final AdminAdvertisementRepository adminAdvertisementRepository;

    /** 관리자 광고내역 조회 */
    public List<AdvertisementListDto> findAllAdvertisementList(int start, int length, MultiValueMap<String, String> formData) {
        int end = start+length;
        DataTableAdvertisementFormDto form = new DataTableAdvertisementFormDto().toDto(formData);
        List<Advertisement> findAdvertisements = adminAdvertisementRepository.findAllAdvertisementList(start, end, form);
        return findAdvertisements.stream().map(a -> new AdvertisementListDto().toDto(a)).collect(Collectors.toList());
    }

    /** 관리자 광고내역 총 갯수 조회 */
    public long findAllAdvertisementTotal(MultiValueMap<String, String> formData) {
        DataTableAdvertisementFormDto form = new DataTableAdvertisementFormDto().toDto(formData);
        return adminAdvertisementRepository.findAllAdvertisementListTotal(form);
    }

    /** 광고 조회 */
    public Advertisement findById(Long id) {
        return adminAdvertisementRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_ADVERTISEMENT));
    }
}
