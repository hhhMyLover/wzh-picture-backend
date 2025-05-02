package com.wzh.wzhpictruebackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = 1376458426890746955L;

    private String userAccount;

    private String userPassword;
}
