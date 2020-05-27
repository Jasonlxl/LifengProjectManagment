package com.sd.lifeng.controller;

import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import com.sd.lifeng.vo.auth.UserRoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author bmr
 * @Classname SystemAuthorityController
 * @Description
 * @Date 2020/5/25 8:43:51
 */
@RestController
@RequestMapping("/lifeng/authorityCtl")
public class SystemAuthorityController extends BaseController{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISystemAuthorityService systemAuthorityService;

    @PostMapping("/getRoleList")
    @VerifyToken
    public ResultVO getRoleList(){
        return ResultVOUtil.success(systemAuthorityService.getRoleList());
    }

    @PostMapping("/getResourceList")
    @VerifyToken
    public ResultVO getResourceList(){
        return ResultVOUtil.success(systemAuthorityService.getResourceList());
    }

    @PostMapping("/addUserRole")
    @VerifyToken
    public ResultVO addUserRole(@RequestBody @Valid UserRoleVO requestVO, BindingResult bindingResult){
        logger.info("【用户分配角色请求】参数列表：{}",requestVO);
        dealBindingResult("用户分配角色",requestVO,bindingResult);
        systemAuthorityService.insertUserRole(requestVO.getUserId(),requestVO.getRoleId());
        return ResultVOUtil.success();
    }


}
