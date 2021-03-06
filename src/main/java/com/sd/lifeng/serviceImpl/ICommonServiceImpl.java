package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.dao.UserDAO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.service.ICommonService;
import com.sd.lifeng.service.ITokenService;
import com.sd.lifeng.vo.auth.RoleVO;
import com.sd.lifeng.vo.user.LoginResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @Classname IBaseServiceImpl
 * @Description
 * @Author bmr
 * @Date 2020/5/24 9:00:51
 */
@Service
public class ICommonServiceImpl implements ICommonService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ITokenService tokenService;


    @Override
    public UserDO getUserInfo() {
        int userId=tokenService.getUserId();
        return userDAO.getUserById(userId);
    }

    @Override
    public Integer getUserId() {
        return tokenService.getUserId();
    }

}
