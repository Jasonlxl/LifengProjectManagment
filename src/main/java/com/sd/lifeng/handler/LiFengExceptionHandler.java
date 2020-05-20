package com.sd.lifeng.handler;

import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author bmr
 * @time 2019-01-21 17:17
 */
@ControllerAdvice
public class LiFengExceptionHandler {


    //捕获
    @ExceptionHandler(value = LiFengException.class)
    @ResponseBody
    public ResultVO handlerSellerException(LiFengException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
