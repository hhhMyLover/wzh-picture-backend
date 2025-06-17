package com.wzh.wzhpictruebackend.exception;


import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -4603801068664013037L;
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode,String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
