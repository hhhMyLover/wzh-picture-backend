package com.wzh.wzhpictruebackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserDTO implements Serializable {

    private String userAccount;

    private String userPassword;

    private String userCheckPassword;
}
