package com.wzh.picture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.picture.constant.UserConstant;
import com.wzh.picture.exception.ErrorCode;
import com.wzh.picture.exception.ThrowUtils;
import com.wzh.picture.model.dto.LoginUserDTO;
import com.wzh.picture.model.dto.RegisterUserDTO;
import com.wzh.picture.model.enums.UserRoleEnum;
import com.wzh.picture.model.po.UserPO;
import com.wzh.picture.model.vo.LoginUserVO;
import com.wzh.picture.service.UserService;
import com.wzh.picture.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 王志禾
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-05-02 22:43:38
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO>
        implements UserService {

    /**
     * 用户注册
     *
     * @param registerUserDTO 用户注册传递的DTO
     * @return 新用户ID
     */
    @Override
    public Long userRegister(RegisterUserDTO registerUserDTO) {
        // 校验参数
        boolean hasBlank = StrUtil.hasBlank(registerUserDTO.getUserAccount(), registerUserDTO.getUserPassword(), registerUserDTO.getUserCheckPassword());
        ThrowUtils.throwIf(hasBlank, ErrorCode.NOT_FOUND_ERROR, "请填写全部参数");

        ThrowUtils.throwIf(registerUserDTO.getUserAccount().length() < 4, ErrorCode.PARAMS_ERROR, "用户账号不足四位");

        ThrowUtils.throwIf(registerUserDTO.getUserPassword().length() < 8, ErrorCode.PARAMS_ERROR, "用户密码不足八位");

        ThrowUtils.throwIf(!registerUserDTO.getUserCheckPassword().equals(registerUserDTO.getUserPassword()), ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        // 校验是否有重复
        LambdaQueryWrapper<UserPO> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(UserPO::getUserAccount, registerUserDTO.getUserAccount());
        UserPO user = this.getOne(userLambdaQueryWrapper);
        ThrowUtils.throwIf(user != null, ErrorCode.PARAMS_ERROR, "账号已存在");

        // 密码加密
        registerUserDTO.setUserPassword(this.userPwdEncrypt(registerUserDTO.getUserPassword()));

        // 保存到数据库
        UserPO registerUser = new UserPO();
        BeanUtil.copyProperties(registerUserDTO, registerUser);
        registerUser.setUserRole(UserRoleEnum.USER.getValue());
        registerUser.setUserName("无名");
        boolean save = this.save(registerUser);

        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "注册失败,数据库错误");
        return registerUser.getId();
    }

    /**
     * 加密密码
     *
     * @param userPassword 用户输入密码
     * @return 加密后的密码
     */
    @Override
    public String userPwdEncrypt(String userPassword) {
        String secretKey = "wzh";
        return DigestUtils.md5DigestAsHex((secretKey + userPassword).getBytes());
    }

    /**
     * 用户登录
     *
     * @param loginUserDTO 前端传递的DTO
     * @param request      HttpServletRequest
     * @return 登录的用户信息(脱敏后)
     */
    @Override
    public LoginUserVO userLogin(LoginUserDTO loginUserDTO, HttpServletRequest request) {
        // 校验参数
        boolean hasBlank = StrUtil.hasBlank(loginUserDTO.getUserAccount(), loginUserDTO.getUserPassword());
        ThrowUtils.throwIf(hasBlank, ErrorCode.NOT_FOUND_ERROR, "请填写全部参数");

        ThrowUtils.throwIf(loginUserDTO.getUserAccount().length() < 4, ErrorCode.PARAMS_ERROR, "用户账号不足四位");

        ThrowUtils.throwIf(loginUserDTO.getUserPassword().length() < 8, ErrorCode.PARAMS_ERROR, "用户密码不足八位");

        // 用户是否存在
        LambdaQueryWrapper<UserPO> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(UserPO::getUserAccount, loginUserDTO.getUserAccount());
        UserPO userPO = this.getOne(userLambdaQueryWrapper);
        ThrowUtils.throwIf(userPO == null || userPO.getId() == null, ErrorCode.PARAMS_ERROR, "用户不存在");

        // 密码是否正确
        String pwdEncrypt = this.userPwdEncrypt(loginUserDTO.getUserPassword()); // 加密
        ThrowUtils.throwIf(!pwdEncrypt.equals(userPO.getUserPassword()), ErrorCode.PARAMS_ERROR, "密码错误");

        // 登录成功,保存session
        LoginUserVO loginUserVO = this.getLoginUserVO(userPO); // 数据脱敏
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, userPO);
        return loginUserVO;
    }


    /**
     * 用户数据脱敏
     *
     * @param user 用户PO
     * @return 脱敏数据
     */
    @Override
    public LoginUserVO getLoginUserVO(UserPO user) {
        if (user == null) return null;
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 用户退出登录
     *
     * @param request HttpServletRequest
     * @return 成功/失败
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request == null) return false;

        this.getLoginUser(request); // 获取当前登录用户  若通过,则代表已经登录

        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, null);
        return true;
    }

    /**
     * 获取当前登录用户
     *
     * @param request HttpServletRequest
     * @return UserPo
     */
    @Override
    public UserPO getLoginUser(HttpServletRequest request) {
        // session 中的用户
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        UserPO loginUser = (UserPO) userObj;
        ThrowUtils.throwIf(loginUser == null || loginUser.getId() == null, ErrorCode.NOT_LOGIN_ERROR);

        // 手动查询用户
        loginUser = this.getById(loginUser.getId());
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);

        return loginUser;
    }
}




