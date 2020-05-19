package com.sd.lifeng.util;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.vo.ResultVO;

import java.util.Map;

/**
 * @author bmr
 * @time 2019-01-11 16:15
 */
public class ResultVOUtil {


    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode("00");
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(String code, String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(ResultCodeEnum codeEnum){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(codeEnum.getCode());
        resultVO.setMsg(codeEnum.getMsg());
        return resultVO;
    }

    public static ResultVO returnResultCodeEnum(ResultCodeEnum enums){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(enums.getCode());
        resultVO.setMsg(enums.getMsg());
        return resultVO;
    }




}
