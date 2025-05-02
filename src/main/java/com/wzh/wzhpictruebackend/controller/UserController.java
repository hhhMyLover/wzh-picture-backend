package com.wzh.wzhpictruebackend.controller;


import com.wzh.wzhpictruebackend.common.BaseResponse;
import com.wzh.wzhpictruebackend.common.ResultUtils;
import com.wzh.wzhpictruebackend.model.dto.RegisterUserDTO;
import com.wzh.wzhpictruebackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    private BaseResponse<Long> userRegister(RegisterUserDTO registerUserDTO){
        Long userId = userService.userRegister(registerUserDTO);
        return ResultUtils.success(userId);
    }

}
