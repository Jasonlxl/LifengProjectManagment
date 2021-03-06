package com.sd.lifeng.util;


import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.vo.ResultVO;

/**
 * @author bmr
 * @time 2019-01-11 16:15
 */
public class ResultVOUtil {


    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setMsg("成功");
        resultVO.setData(null);
        return resultVO;
    }

    /**
     * 返回成功
     * @param msg 成功信息描述
     * @return
     */
    public static ResultVO success(String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setMsg(msg);
        return resultVO;
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
