package com.wzh.wzhpictruebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.wzhpictruebackend.exception.ErrorCode;
import com.wzh.wzhpictruebackend.exception.ThrowUtils;
import com.wzh.wzhpictruebackend.model.dto.LoginUserDTO;
import com.wzh.wzhpictruebackend.model.dto.RegisterUserDTO;
import com.wzh.wzhpictruebackend.model.enums.UserRoleEnum;
import com.wzh.wzhpictruebackend.model.po.User;
import com.wzh.wzhpictruebackend.model.vo.LoginUserVO;
import com.wzh.wzhpictruebackend.service.UserService;
import com.wzh.wzhpictruebackend.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 王志禾
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-05-02 22:43:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
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
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserAccount, registerUserDTO.getUserAccount());
        User user = this.getOne(userLambdaQueryWrapper);
        ThrowUtils.throwIf(user != null, ErrorCode.PARAMS_ERROR, "账号已存在");

        // 密码加密
        registerUserDTO.setUserPassword(this.userPwdEncrypt(registerUserDTO.getUserPassword()));

        // 保存到数据库
        User registerUser = new User();
        BeanUtil.copyProperties(registerUserDTO, registerUser);
        registerUser.setUserRole(UserRoleEnum.USER.getValue());
        registerUser.setUserName("无名");
        boolean save = this.save(registerUser);

        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "注册失败,数据库错误");
        return registerUser.getId();
    }

    @Override
    public String userPwdEncrypt(String userPassword) {
        String secretKey = "wzh";
        return DigestUtils.md5DigestAsHex((secretKey + userPassword).getBytes());
    }

    @Override
    public LoginUserVO userLogin(LoginUserDTO loginUserDTO, HttpServletRequest request) {
        // 校验参数
        boolean hasBlank = StrUtil.hasBlank(loginUserDTO.getUserAccount(), loginUserDTO.getUserPassword());
        ThrowUtils.throwIf(hasBlank, ErrorCode.NOT_FOUND_ERROR, "请填写全部参数");

        ThrowUtils.throwIf(loginUserDTO.getUserAccount().length() < 4, ErrorCode.PARAMS_ERROR, "用户账号不足四位");

        ThrowUtils.throwIf(loginUserDTO.getUserPassword().length() < 8, ErrorCode.PARAMS_ERROR, "用户密码不足八位");

        // 用户是否存在
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserAccount, loginUserDTO.getUserAccount());
        User loginUser = this.getOne(userLambdaQueryWrapper);
        ThrowUtils.throwIf(loginUser == null || loginUser.getId() == null, ErrorCode.PARAMS_ERROR, "用户不存在");

        // 密码是否正确
        String pwdEncrypt = this.userPwdEncrypt(loginUserDTO.getUserPassword()); // 加密
        ThrowUtils.throwIf(!pwdEncrypt.equals(loginUser.getUserPassword()), ErrorCode.PARAMS_ERROR, "密码错误");

        // 登录成功,保存session
        request.getSession().setAttribute("user_login_status",loginUser);
        return this.getLoginUser(loginUser);
    }

    @Override
    public LoginUserVO getLoginUser(User loginUser) {
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(loginUser,loginUserVO);
        return loginUserVO;
    }
}




