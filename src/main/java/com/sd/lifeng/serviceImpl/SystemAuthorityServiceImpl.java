package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.dao.ResourceDao;
import com.sd.lifeng.dao.RoleDao;
import com.sd.lifeng.dao.SystemAuthorityDAO;
import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bmr
 * @Classname SystemAuthorityServiceImpl
 * @Description
 * @Date 2020/5/25 8:42:51
 */
@Service
public class SystemAuthorityServiceImpl implements ISystemAuthorityService {

    @Autowired
    private SystemAuthorityDAO systemAuthorityDAO;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private IUserCategoryService userCategoryService;

    @Override
    public List<RoleVO> getRoleList() {
        return roleDao.getRoleList();
    }

    @Override
    public List<ResourceVO> getResourceList() {
        return resourceDao.getResourceList();
    }


    @Override
    public void editRole(SystemRolesDO rolesDO) {
        int row;
        if( rolesDO.getId() != 0){
            row=roleDao.updateRole(rolesDO);
        }else{
            row=roleDao.addRole(rolesDO);
        }

        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteRole(int roleId) {
        int row;

        row = roleDao.deleteRole(roleId);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void insertUserRole(int userId, int roleId) {
        int row;
        UserDO userDO = userCategoryService.findUserById(userId);
        if(userDO == null){
            throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
        }

        SystemRolesDO rolesDO = roleDao.getRoleById(roleId);
        if(rolesDO == null){
            throw new LiFengException(ResultCodeEnum.ROLE_NOT_EXIST);
        }

        row=systemAuthorityDAO.addUserRole(userId,roleId);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void insertRoleResource(int roleId, int resourceId) {
        int row;
        row=systemAuthorityDAO.addRoleResource(roleId,resourceId);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void editRoleResourceBatch(int roleId, List<Integer> resourceIdList) {
        int row;
        try {
            row=systemAuthorityDAO.editRoleResourceBatch(roleId,resourceIdList);
        }catch (Exception e){
            throw new LiFengException(ResultCodeEnum.SERVER_EXCEPTION.getCode(),e.getMessage());
        }

        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void removeUserRole(int userId, int roleId) {
        int row;
        if(!systemAuthorityDAO.getUserRoleByUserIdAndRoleId(userId,roleId)){
            throw new LiFengException(ResultCodeEnum.USER_ROLE_NOT_EXIST);
        }
        row=systemAuthorityDAO.removeUserRole(userId,roleId);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public List<RoleVO> getUserRoles(int userId) {
        UserDO userDO = userCategoryService.findUserById(userId);
        if(userDO == null){
            throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
        }
        return systemAuthorityDAO.getRolesByUserId(userId);
    }

    @Override
    public List<ResourceVO> getRoleResources(int roleId) {
        if((roleDao.getRoleById(roleId)) == null){
            throw new LiFengException(ResultCodeEnum.ROLE_NOT_EXIST);
        }
        return systemAuthorityDAO.getResourcesByRoleId(roleId);
    }

    @Override
    public void removeRoleResource(int roleId, int resourceId) {
        int row;
        if(!systemAuthorityDAO.getRoleResourceByRoleIdAndResourceId(roleId,resourceId)){
            throw new LiFengException(ResultCodeEnum.ROLE_RESOURCE_NOT_EXIST);
        }
        row=systemAuthorityDAO.removeRoleResource(roleId,resourceId);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void removeRoleResourceBatch(int roleId, List<Integer> resourceIdList) {
        int row;
        resourceIdList.forEach(resourceId->{
            if(!systemAuthorityDAO.getRoleResourceByRoleIdAndResourceId(roleId,resourceId)){
                throw new LiFengException(ResultCodeEnum.ROLE_RESOURCE_NOT_EXIST);
            }
        });
        try {
            row=systemAuthorityDAO.removeRoleResourceBatch(roleId,resourceIdList);
        }catch (Exception e){
            throw new LiFengException(ResultCodeEnum.SERVER_EXCEPTION.getCode(),e.getMessage());
        }

        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }
}
