package com.sd.lifeng.controller;

import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author bmr
 * @classname BaseController
 * @description
 * @date 2020/5/27 18:09:51
 */
@RestController
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理前端传递参数不正确
     * @param logType 日志请求类型
     * @param requestVO 请求参数实体
     */
    protected void dealBindingResult(String logType, Object requestVO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("【"+logType+"请求】参数不正确，requestVo={}",requestVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }
}
