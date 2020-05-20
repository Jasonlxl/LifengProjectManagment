package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.config.JwtConfig;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.util.TokenUtil;
import com.sd.lifeng.vo.LoginRequestVO;
import com.sd.lifeng.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/lifeng/userctl")
@CrossOrigin
public class UserCategoryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserCategoryService userCategoryService;

    @Autowired
    private JwtConfig jwtConfig;


    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid  LoginRequestVO loginRequestVO, BindingResult bindingResult){
        logger.info("register send into msg :"+loginRequestVO);

        if(bindingResult.hasErrors()){
            logger.error("【登录请求】参数不正确，loginRequestVO={}",loginRequestVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        UserDO userDO =userCategoryService.login(loginRequestVO.getUserName(),loginRequestVO.getPassword());
        if(userDO == null){
            throw new LiFengException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生产jwt
        String token=TokenUtil.getToken(userDO.getId().toString(), jwtConfig.getKey());

        Map<String,String> map=new HashMap<>();
        map.put("token",token);


        logger.info("response:"+map);
        return ResultVOUtil.success(map);
    }
}
