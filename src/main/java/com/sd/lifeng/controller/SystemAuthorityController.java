package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.annotion.VerifyToken;
import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.util.ResultVOUtil;
import com.sd.lifeng.vo.ResultVO;
import com.sd.lifeng.vo.auth.RoleResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import com.sd.lifeng.vo.auth.UserRoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/removeUserRole")
    @VerifyToken
    public ResultVO removeUserRole(@RequestBody @Valid UserRoleVO requestVO, BindingResult bindingResult){
        logger.info("【移除用户角色请求】参数列表：{}",requestVO);
        dealBindingResult("移除用户角色",requestVO,bindingResult);
        systemAuthorityService.removeUserRole(requestVO.getUserId(),requestVO.getRoleId());
        return ResultVOUtil.success();
    }

    @PostMapping("/editRoleResource")
    @VerifyToken
    public ResultVO editUserRole(@RequestBody @Valid RoleResourceVO requestVO, BindingResult bindingResult){
        logger.info("【为角色分配资源请求】参数列表：{}",requestVO);
        dealBindingResult("角色分配资源",requestVO,bindingResult);
        systemAuthorityService.editRoleResourceBatch(requestVO.getRoleId(),requestVO.getResourceIdList());
        return ResultVOUtil.success();
    }

    @PostMapping("/getUserRoles")
    @VerifyToken
    public ResultVO getUserRoles(@RequestBody JSONObject req){
        logger.info("【获取用户所有角色列表请求】参数列表：{}",req);
        int userId = req.getInteger("userId");
        if(userId <= 0){
            return ResultVOUtil.error(ResultCodeEnum.PARAM_ERROR.getCode(),"userId不能为空");
        }
        return ResultVOUtil.success(systemAuthorityService.getUserRoles(userId));
    }

    @PostMapping("/getRoleResources")
    @VerifyToken
    public ResultVO getRoleResources(@RequestBody JSONObject req){
        logger.info("【获取角色下所有资源列表请求】参数列表：{}",req);
        int roleId = req.getInteger("roleId");
        if(roleId <= 0){
            return ResultVOUtil.error(ResultCodeEnum.PARAM_ERROR.getCode(),"roleId不能为空");
        }
        return ResultVOUtil.success(systemAuthorityService.getRoleResources(roleId));
    }

//    @PostMapping("/removeRoleResource")
//    @VerifyToken
//    public ResultVO removeRoleResource(@RequestBody @Valid RoleResourceVO requestVO, BindingResult bindingResult){
//        logger.info("【移除角色资源请求】参数列表：{}",requestVO);
//        dealBindingResult("移除角色资源",requestVO,bindingResult);
//        systemAuthorityService.removeRoleResourceBatch(requestVO.getRoleId(),requestVO.getResourceIdList());
//        return ResultVOUtil.success();
//    }

    @PostMapping("/addRole")
    @VerifyToken
    public ResultVO addRole(@RequestBody RoleVO requestVO, BindingResult bindingResult){
        logger.info("【添加角色请求】参数列表：{}",requestVO);
        dealBindingResult("添加角色请求",requestVO,bindingResult);
        SystemRolesDO systemRolesDO =new SystemRolesDO();
        systemRolesDO.setRoleName(requestVO.getRoleName());
        systemRolesDO.setSystemManager(requestVO.getSystemManager());
        systemAuthorityService.editRole(systemRolesDO);
        return ResultVOUtil.success();
    }

    @PostMapping("/updateRole")
    @VerifyToken
    public ResultVO updateRole(@RequestBody JSONObject jsonObject){
        if(jsonObject.getInteger("id") == null || jsonObject.getInteger("id") == 0){
            return ResultVOUtil.error(ResultCodeEnum.PARAM_ERROR.getCode(),"角色id不能为空");
        }

        SystemRolesDO systemRolesDO =new SystemRolesDO();
        systemRolesDO.setId(jsonObject.getInteger("id"));
        systemRolesDO.setRoleName(jsonObject.getString("roleName"));
        systemRolesDO.setSystemManager(jsonObject.getInteger("systemManager"));
        logger.info("【修改角色请求】参数列表：{}",systemRolesDO);
        systemAuthorityService.editRole(systemRolesDO);
        return ResultVOUtil.success();
    }

    @PostMapping("/deleteRole")
    @VerifyToken
    public ResultVO deleteRole(@RequestBody JSONObject jsonObject){
        int id = jsonObject.getInteger("id");
        if( id == 0 ){
            return ResultVOUtil.error(ResultCodeEnum.PARAM_ERROR.getCode(),"角色id不能为空");
        }

        systemAuthorityService.deleteRole(id);
        return ResultVOUtil.success();
    }
}
