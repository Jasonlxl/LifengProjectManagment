package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.config.JwtConfig;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.util.TokenUtil;
import com.sd.lifeng.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/lifeng/userCtl")
@CrossOrigin
public class UserCategoryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserCategoryService userCategoryService;


    @PostMapping("/register")
    public ResultVO register(@RequestBody @Valid RegisterRequestVO requestVO, BindingResult bindingResult){
        logger.info("【注册请求】参数列表：{}",requestVO);

        if(bindingResult.hasErrors()){
            logger.error("【注册请求】参数不正确，requestVO={}",requestVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        userCategoryService.register(requestVO.getUserName(),requestVO.getPassword(),requestVO.getRealName());

        return ResultVOUtil.success("注册成功，请等待审核");
    }

    @PostMapping("/getRegisterList")
    public ResultVO getRegisterList(@RequestBody JSONObject jsonObject){
        logger.info("【获取用户注册列表】参数列表：{}",jsonObject);
        String status=jsonObject.getString("status");
        List<RegisterResponseVO> registerResponseVOS =userCategoryService.getRegisterList(status);
        return ResultVOUtil.success(registerResponseVOS );
    }


    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid  LoginRequestVO loginRequestVO, BindingResult bindingResult){
        logger.info("【登录请求】请求参数：{}",loginRequestVO);

        if(bindingResult.hasErrors()){
            logger.error("【登录请求】参数不正确，loginRequestVO={}",loginRequestVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        LoginResponseVO loginResponseVO =userCategoryService.login(loginRequestVO.getUserName(),loginRequestVO.getPassword());

        logger.info("【登录请求】返回数据:{}",loginResponseVO);
        return ResultVOUtil.success(loginResponseVO);
    }

    @PostMapping("/loginOut")
    public ResultVO loginOut(){
        return ResultVOUtil.success();
    }

    @PostMapping("/getUserList")
    public ResultVO getUserList(){
        return ResultVOUtil.success(userCategoryService.getUserList() );
    }

    /**
     * 重置密码
     * @return
     */
    @PostMapping("/resetPassword")
    public ResultVO resetPassword(@RequestBody @Valid ResetPasswordVO resetPasswordVO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("【重置密码请求】参数不正确，resetPasswordVO={}",resetPasswordVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        userCategoryService.resetPassword(resetPasswordVO.getUserId(),resetPasswordVO.getNewPassword());
        return ResultVOUtil.success();
    }

    /**
     * 修改密码
     * @return
     */
    @PostMapping("/changePassword")
    public ResultVO changePassword(@RequestBody @Valid ChangePasswordVO changePasswordVO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("【修改密码请求】参数不正确，changePasswordVO={}",changePasswordVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        userCategoryService.changePassword(changePasswordVO.getNewPassword());
        return ResultVOUtil.success();
    }



}
