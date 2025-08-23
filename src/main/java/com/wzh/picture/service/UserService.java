package com.wzh.picture.service;

import com.wzh.picture.model.dto.LoginUserDTO;
import com.wzh.picture.model.dto.RegisterUserDTO;
import com.wzh.picture.model.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.picture.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 王志禾
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-05-02 22:43:38
 */
public interface UserService extends IService<UserPO> {

    /**
     * 用户注册
     *
     * @param registerUserDTO 用户注册传递的DTO
     * @return 新用户ID
     */
    Long userRegister(RegisterUserDTO registerUserDTO);

    /**
     * 加密密码
     *
     * @param userPassword 用户输入密码
     * @return 加密后的密码
     */
    String userPwdEncrypt(String userPassword);

    /**
     * 用户登录
     *
     * @param loginUserDTO 前端传递的DTO
     * @param request      HttpServletRequest
     * @return 登录的用户信息(脱敏后)
     */
    LoginUserVO userLogin(LoginUserDTO loginUserDTO, HttpServletRequest request);

    /**
     * 用户数据脱敏
     *
     * @param user 用户PO
     * @return 脱敏数据
     */
    LoginUserVO getLoginUserVO(UserPO user);

    /**
     * 用户退出登录
     *
     * @param request HttpServletRequest
     * @return 成功/失败
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request HttpServletRequest
     * @return UserPo
     */
    UserPO getLoginUser(HttpServletRequest request);
}
