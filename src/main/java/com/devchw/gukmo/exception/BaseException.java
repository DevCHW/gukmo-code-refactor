package com.devchw.gukmo.exception;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;
}
