package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.admin.dto.api.advertisement.UpdateAdvertisementRequest;
import com.devchw.gukmo.admin.repository.AdminAdvertisementRepository;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.utils.DateUtil;
import com.devchw.gukmo.utils.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileManager fileManager;

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

    /** 광고 삭제 */
    @Transactional
    public void delete(Long id) {
        Advertisement findAdvertisement = adminAdvertisementRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_ADVERTISEMENT));
        fileManager.delete(findAdvertisement.getFileName());    //파일 삭제 이후 데이터 삭제
        adminAdvertisementRepository.deleteById(id);
    }

    /** 광고 수정 */
    @Transactional
    public Long edit(Long id, UpdateAdvertisementRequest request, MultipartFile file) {
        Advertisement findAdvertisement = adminAdvertisementRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_ADVERTISEMENT));

        if(file != null) { //광고사진 업로드를 하였을 때
            fileManager.delete(findAdvertisement.getFileName()); //기존 광고사진 삭제
            String savedFileName = fileManager.save(file); //새로운 파일 저장
            findAdvertisement.change(DateUtil.stringToLocalDateTimeConverter(request.getStartDate()), DateUtil.stringToLocalDateTimeConverter(request.getEndDate()), savedFileName);
        } else { //광고사진 업로드 하지 않았을 때.
            findAdvertisement.change(DateUtil.stringToLocalDateTimeConverter(request.getStartDate()), DateUtil.stringToLocalDateTimeConverter(request.getEndDate()));
        }
        return findAdvertisement.getId();
    }

    /** 모든 광고목록 조회 */
    public List<AdvertisementDto> findAll() {
        List<Advertisement> findAdvertisementList = adminAdvertisementRepository.findAll();
        return findAdvertisementList.stream().map(a -> new AdvertisementDto().toDto(a)).collect(Collectors.toList());
    }
}
