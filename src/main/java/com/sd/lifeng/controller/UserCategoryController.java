package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.*;
import com.sd.lifeng.vo.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        dealBindingResult("用户注册",requestVO,bindingResult);

        userCategoryService.register(requestVO.getUserName(),requestVO.getPassword(),requestVO.getRealName());
        return ResultVOUtil.success("注册成功，请等待审核");
    }

    @PostMapping("/audit")
    public ResultVO userAudit(@RequestBody @Valid UserAuditVO requestVO, BindingResult bindingResult){
        logger.info("【用户审核请求】参数列表：{}",requestVO);
        dealBindingResult("用户审核",requestVO,bindingResult);

        return ResultVOUtil.success();
    }
    /**
     * 用户登录
     * @param requestVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid LoginRequestVO requestVO, BindingResult bindingResult){
        logger.info("【登录请求】请求参数：{}",requestVO);
        dealBindingResult("登录",requestVO,bindingResult);

        LoginResponseVO loginResponseVO =userCategoryService.login(requestVO.getUserName(),requestVO.getPassword());
        logger.info("【登录请求】返回数据:{}",loginResponseVO);
        return ResultVOUtil.success(loginResponseVO);
    }

    @VerifyToken
    @PostMapping("/getRegisterList")
    public ResultVO getRegisterList(@RequestBody JSONObject jsonObject){
        logger.info("【获取用户注册列表】参数列表：{}",jsonObject);
        String status=jsonObject.getString("status");
        List<RegisterResponseVO> registerResponseVOList =userCategoryService.getRegisterList(status);
        return ResultVOUtil.success(registerResponseVOList );
    }

    @PostMapping("/loginOut")
    public ResultVO loginOut(){
        return ResultVOUtil.success();
    }

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/getUserList")
    public ResultVO getUserList(){
        return ResultVOUtil.success(userCategoryService.getUserList() );
    }

    /**
     * 重置密码
     * @return
     */
    @PostMapping("/resetPassword")
    public ResultVO resetPassword(@RequestBody @Valid ResetPasswordVO requestVO, BindingResult bindingResult){
        dealBindingResult("重置密码",requestVO,bindingResult);

        userCategoryService.resetPassword(requestVO.getUserId(),requestVO.getNewPassword());
        return ResultVOUtil.success();
    }

    /**
     * 修改密码
     * @return
     */
    @PostMapping("/changePassword")
    public ResultVO changePassword(@RequestBody @Valid ChangePasswordVO requestVO, BindingResult bindingResult){
        dealBindingResult("修改密码",requestVO,bindingResult);
        userCategoryService.changePassword(requestVO.getNewPassword());
        return ResultVOUtil.success();
    }

    /**
     * 处理前端传递参数不正确
     * @param logType 日志请求类型
     * @param requestVO 请求参数实体
     */
    private void dealBindingResult(String logType,Object requestVO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("【"+logType+"请求】参数不正确，requestVo={}",requestVO);
            throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }

}
