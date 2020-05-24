package com.sd.lifeng.controller;

import com.sd.lifeng.service.IUserTypeService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserTypesController
 * @Description
 * @Author bmr
 * @Date 2020/5/24 8:34:51
 */
@RestController
@CrossOrigin
@RequestMapping("/lifeng/userTypeCtl")
public class UserTypeController {
    @Autowired
    private IUserTypeService userTypeService;

    @PostMapping("/getUserTypeList")
    public ResultVO getUserList(){
        return ResultVOUtil.success(userTypeService.getUserTypeList());
    }
}
