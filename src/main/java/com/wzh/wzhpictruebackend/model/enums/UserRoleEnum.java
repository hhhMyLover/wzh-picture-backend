package com.wzh.wzhpictruebackend.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("管理员","admin"),
    USER("用户","user");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value){
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getEnumByValue(String value){
        if (StrUtil.isBlank(value)) return null;
        for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
            if (value.equals(userRoleEnum.getValue())){
                return userRoleEnum;
            }
        }
        return null;
    }

}
