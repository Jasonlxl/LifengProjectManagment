package com.sd.lifeng.exception;

import com.sd.lifeng.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * @author bmr
 * @time 2019-01-14 9:46
 */
@Getter
public class LiFengException extends RuntimeException{

    private String code;

    public LiFengException(ResultCodeEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public LiFengException(String code, String message) {
        super(message);
        this.code = code;
    }

    public LiFengException(String message) {
        super(message);
    }
}
