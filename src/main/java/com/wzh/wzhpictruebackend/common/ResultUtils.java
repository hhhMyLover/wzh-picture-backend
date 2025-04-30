package com.wzh.wzhpictruebackend.common;

import com.wzh.wzhpictruebackend.exception.ErrorCode;

public class ResultUtils {

    /**
     * 成功
     * @param <T> 数据类型
     * @param data 数据
     * @return  响应数据
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }


    /**
     * 错误
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message){
        return new BaseResponse<>(errorCode.getCode(),null,message);
    }

    /**
     * 失败
     *
     * @param code 错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BaseResponse<?> error(int code, String message){
        return new BaseResponse<>(code,null,message);
    }

}
