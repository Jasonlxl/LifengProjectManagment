package com.sd.lifeng.handler;

import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname GlobalExceptionHandler
 * @Description 全局的异常捕获处理
 * @Date 2020/5/19 8:56 :51
 * @Created by bmr
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO handlerException(Exception e){
        e.printStackTrace();
        log.info("服务器发生异常:{}",e.getMessage());
        return ResultVOUtil.error(ResultCodeEnum.SERVER_EXCEPTION.getCode(),e.getMessage());
    }
}
