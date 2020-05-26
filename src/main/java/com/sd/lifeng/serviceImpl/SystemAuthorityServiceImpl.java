package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.dao.SystemAuthorityDAO;
import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.vo.ResourceVO;
import com.sd.lifeng.vo.RoleVO;
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

    @Override
    public List<RoleVO> getRoleList() {
        return systemAuthorityDAO.getRoleList();
    }

    @Override
    public List<ResourceVO> getResourceList() {
        return systemAuthorityDAO.getResourceList();
    }

    @Override
    public void editRole(SystemRolesDO rolesDO) {
        int row;
        if(rolesDO.getId() != null && rolesDO.getId() != 0){
            row=systemAuthorityDAO.updateRole(rolesDO);
        }else{
            row=systemAuthorityDAO.addRole(rolesDO);
        }

        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void insertUserRole(int userId, int roleId) {
        int row;
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
}
