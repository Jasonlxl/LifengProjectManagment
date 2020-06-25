package com.sd.lifeng.service;

import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;

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
     * @Return java.util.List<com.sd.lifeng.vo.auth.RoleVO>
     */
    List<RoleVO> getRoleList();

    /**
     * @Description 获取系统资源列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:50 :51
     * @Return java.util.List<com.sd.lifeng.vo.auth.ResourceVO>
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
     * @description 删除系统角色
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/25 : 8:59 :51
     */
    void deleteRole(int roleId);

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

    /**
     * @description 为角色分配资源  批量
     * @param roleId 角色id
     * @param resourceIdList 资源id集合
     * @author bmr
     * @date 2020/5/26 : 17:27 :51
     * @return void
     */
    void editRoleResourceBatch(int roleId, List<Integer> resourceIdList);

    /**
     * @description 移除用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/26 : 17:26 :51
     * @return void
     */
    void removeUserRole(int userId,int roleId);

    /**
     * 获取用户的角色列表
     * @param userId 用户id
     * @return
     */
    List<RoleVO> getUserRoles(int userId);

    /**
     * 获取角色下的所有资源列表
     * @param roleId 角色id
     * @return
     */
    List<ResourceVO> getRoleResources(int roleId);
    /**
     * @description 移除角色资源
     * @param roleId 角色id
     * @param resourceId 资源id
     * @author bmr
     * @date 2020/5/26 : 17:26 :51
     * @return void
     */
    void removeRoleResource(int roleId,int resourceId);

    /**
     * @description 移除角色资源  批量
     * @param roleId 角色id
     * @param resourceIdList 资源id集合
     * @author bmr
     * @date 2020/5/26 : 17:26 :51
     * @return void
     */
    void removeRoleResourceBatch(int roleId,List<Integer> resourceIdList);
}
