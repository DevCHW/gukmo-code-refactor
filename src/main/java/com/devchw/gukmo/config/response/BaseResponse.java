package com.devchw.gukmo.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.devchw.gukmo.config.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "status", "message", "result"})
public class BaseResponse<T> {
    private final Boolean success;
    private final String message;
    private final int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.success = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.status = SUCCESS.getStatus();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status){
        this.success = status.isSuccess();
        this.message = status.getMessage();
        this.status = status.getStatus();
    }

    // @Valid 예외 처리
    public BaseResponse(ErrorResponse errorResponse){
        BaseResponseStatus status = BaseResponseStatus.VALIDATED_ERROR;
        this.success = status.isSuccess();
        this.status = status.getStatus();
        this.message = errorResponse.getMessage();
    }
}
