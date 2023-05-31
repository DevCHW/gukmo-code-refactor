package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.api.penalty.SavePenaltyRequest;
import com.devchw.gukmo.admin.service.PenaltyService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.penalty.Penalty;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.devchw.gukmo.config.response.BaseResponseStatus.INTERNAL_SERVER_ERROR;
import static com.devchw.gukmo.config.response.BaseResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/penalty")
public class PenaltyApiController {

    private final PenaltyService penaltyService;

    @PostMapping
    public BaseResponse<String> savePenalty(SavePenaltyRequest request) {
        Penalty savedPenalty = penaltyService.save(request);
        log.info("SavePenaltyRequest={}", request);
        if(savedPenalty != null) {
            return new BaseResponse<>(SUCCESS);
        } else {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }
}
