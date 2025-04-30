package com.wzh.wzhpictruebackend.exception;


import com.wzh.wzhpictruebackend.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessException (BusinessException e){
        log.error("BusinessException",e);
        return new BaseResponse<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> businessException (RuntimeException e){
        log.error("RuntimeException",e);
        return new BaseResponse<>(ErrorCode.SYSTEM_ERROR);
    }
}
