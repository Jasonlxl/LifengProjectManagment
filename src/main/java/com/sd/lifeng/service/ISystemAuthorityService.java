package com.sd.lifeng.service;

import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.ResourceVO;
import com.sd.lifeng.vo.RoleVO;

import java.util.List;

/**
 * @author bmr
 * @Classname ISystemAuthorityService
 * @Description
 * @Date 2020/5/25 8:41:51
 */
public interface ISystemAuthorityService {
    /**
     * @Description 获取系统角色列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:41 :51 
     * @Return java.util.List<com.sd.lifeng.vo.RoleVO>
     */
    List<RoleVO> getRoleList();

    /**
     * @Description 获取系统资源列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:50 :51
     * @Return java.util.List<com.sd.lifeng.vo.ResourceVO>
     */
    List<ResourceVO> getResourceList();

    /**
     * @description 编辑系统角色
     * @param rolesDO
     * @author bmr
     * @date 2020/5/25 : 8:59 :51
     */
    void editRole(SystemRolesDO rolesDO);

    /**
     * @description 为用户分配角色
     * @param userId 用户id
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/26 : 17:26 :51
     * @return void
     */
    void insertUserRole(int userId,int roleId);

    /**
     * @description 为角色分配资源
     * @param roleId 角色id
     * @param resourceId 资源id
     * @author bmr
     * @date 2020/5/26 : 17:27 :51
     * @return void
     */
    void insertRoleResource(int roleId,int resourceId);
}
