package com.sd.lifeng.controller;

import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bmr
 * @Classname SystemAuthorityController
 * @Description
 * @Date 2020/5/25 8:43:51
 */
@RestController
@RequestMapping("/lifeng/authorityCtl")
public class SystemAuthorityController {
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
    public ResultVO addUserRole(){
//        systemAuthorityService.insertUserRole();
        return ResultVOUtil.success();
    }
}
