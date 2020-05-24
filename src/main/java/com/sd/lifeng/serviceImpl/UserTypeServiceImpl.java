package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.dao.UserTypeDAO;
import com.sd.lifeng.service.IUserTypeService;
import com.sd.lifeng.vo.UserTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname UserTypeServiceImpl
 * @Description
 * @Author bmr
 * @Date 2020/5/24 8:45:51
 */
@Service
public class UserTypeServiceImpl implements IUserTypeService {
    @Autowired
    private UserTypeDAO userTypeDAO;

    @Override
    public List<UserTypeVO> getUserTypeList() {
        return userTypeDAO.getUserTypeList();
    }
}
