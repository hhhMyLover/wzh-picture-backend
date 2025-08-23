package com.wzh.picture.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserDTO implements Serializable {

    private static final long serialVersionUID = -3883998631439584391L;

    private String userAccount;

    private String userPassword;

    private String userCheckPassword;
}
