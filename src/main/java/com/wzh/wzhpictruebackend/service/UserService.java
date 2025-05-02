package com.wzh.wzhpictruebackend.service;

import com.wzh.wzhpictruebackend.model.dto.RegisterUserDTO;
import com.wzh.wzhpictruebackend.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 王志禾
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-05-02 22:43:38
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param registerUserDTO 用户注册传递的DTO
     * @return 新用户ID
     */
    Long userRegister(RegisterUserDTO registerUserDTO);

    String userPwdEncrypt(String userPassword);

}
