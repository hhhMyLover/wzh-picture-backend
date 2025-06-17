package com.wzh.wzhpictruebackend.controller;


import com.wzh.wzhpictruebackend.annotation.AuthCheck;
import com.wzh.wzhpictruebackend.common.BaseResponse;
import com.wzh.wzhpictruebackend.common.ResultUtils;
import com.wzh.wzhpictruebackend.constant.UserConstant;
import com.wzh.wzhpictruebackend.model.dto.LoginUserDTO;
import com.wzh.wzhpictruebackend.model.dto.RegisterUserDTO;
import com.wzh.wzhpictruebackend.model.po.UserPO;
import com.wzh.wzhpictruebackend.model.vo.LoginUserVO;
import com.wzh.wzhpictruebackend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(RegisterUserDTO registerUserDTO){
        Long userId = userService.userRegister(registerUserDTO);
        return ResultUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(LoginUserDTO loginUserDTO, HttpServletRequest request){
        LoginUserVO loginUserVO = userService.userLogin(loginUserDTO, request);
        return ResultUtils.success(loginUserVO);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout( HttpServletRequest request){
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/get/loginUser")
    public BaseResponse<LoginUserVO> getLoinUser( HttpServletRequest request){
        UserPO userPO = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(userPO));
    }

}
