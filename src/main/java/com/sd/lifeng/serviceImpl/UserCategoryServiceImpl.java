package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.dao.UserDAO;
import com.sd.lifeng.domain.RegisterDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.dto.UserDTO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.enums.UserAuditEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.ICommonService;
import com.sd.lifeng.service.ISystemAuthorityService;
import com.sd.lifeng.service.ITokenService;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.util.RandomUtil;
import com.sd.lifeng.vo.user.LoginResponseVO;
import com.sd.lifeng.vo.user.RegisterResponseVO;
import com.sd.lifeng.vo.user.UserAddVO;
import com.sd.lifeng.vo.user.UserListVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2020/5/18.
 */
@Service
public class UserCategoryServiceImpl implements IUserCategoryService {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private ISystemAuthorityService systemAuthorityService;


    /**
     * @Description 用户注册
     * @param userName 用户名
     * @param password 密码
     * @param realName 真实姓名
     * @Auther bmr
     * @Date 2020/5/23 : 14:20 :51
     * @Return void
     */
    @Override
    public void register(String userName, String password, String realName) {
        //判断用户名是否存在
        RegisterDO registerDO=userDAO.getRegisterUserByPhone(userName);
        if(registerDO != null){
            throw new LiFengException(ResultCodeEnum.USER_HAS_REGISTERED);
        }
        //生成密码
        String salt= RandomUtil.generatorRandom(4,2);
        String finalPassword= DigestUtils.md5Hex(userName + salt + password);

        //插入数据
        RegisterDO dto=new RegisterDO();
        dto.setTelNo(userName);
        dto.setPassword(finalPassword);
        dto.setSalt(salt);
        dto.setRealName(realName);
        dto.setStatus(UserAuditEnum.PRE_AUDIT.getValue());
        int count=userDAO.addRegister(dto);
        if(count == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }

    }

    /**
     * 登录
     * @param userName  用户名
     * @param password  密码
     * @return
     */
    @Override
    public LoginResponseVO login(String userName, String password){
        UserDO userDO=userDAO.getUserByPhone(userName);

        String finalPassword;
        if(userDO == null){
            //如果用户表中不存在  去查询注册表中的信息
            RegisterDO registerUser = userDAO.getRegisterUserByPhone(userName);
            if(registerUser == null){
                throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
            }

            finalPassword=DigestUtils.md5Hex(registerUser.getTelNo() + registerUser.getSalt() + password);
            if(!finalPassword.equals(registerUser.getPassword())){
                throw new LiFengException(ResultCodeEnum.LOGIN_ERROR);
            }

            if(registerUser.getStatus() == 0){
                throw new LiFengException(ResultCodeEnum.LOGIN_ERROR.getCode(),"用户待审核，审核通过后可登录");
            }

            if(registerUser.getStatus() == 2){
                throw new LiFengException(ResultCodeEnum.LOGIN_ERROR.getCode(),"用户审核被拒，审核通过后可登录");
            }

        }

        finalPassword =  DigestUtils.md5Hex(userDO.getTelno() + userDO.getSalt() + password);

        if(!finalPassword.equals(userDO.getPasswd())){
            throw new LiFengException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生成jwt
        String token= tokenService.createToken(userDO.getId().toString());

        LoginResponseVO responseVO=userDAO.getUserDetailById(userDO.getId());
        responseVO.setToken(token);

        return responseVO;
    }


    /**
     * @Description 根据userId获取用户信息
     * @param userId  用户id
     * @Auther bmr
     * @Date 2020/5/21 : 8:17 :51
     * @Return com.sd.lifeng.domain.UserDO
     */
    @Override
    public UserDO findUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public void changePassword(String oldPassword,String newPassword) {
        UserDO userDO=commonService.getUserInfo();
        //校验旧密码是否一样
        String oldFinalPassword=DigestUtils.md5Hex(userDO.getTelno() + userDO.getSalt() + oldPassword);
        if(!oldFinalPassword.equals(userDO.getPasswd())){
            throw new LiFengException(ResultCodeEnum.USER_PASSWORD_ERROR);
        }

        //生成新密码
        String finalPassword= DigestUtils.md5Hex(userDO.getTelno() + userDO.getSalt() + newPassword);
        int flag=userDAO.changeUserPassword(userDO.getId(),finalPassword);
        if(flag == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public void resetPassword(int userId, String newPassword) {
        //只有管理员才可以重置密码
        if(! userDAO.isSystemManagerByUserId(tokenService.getUserId())){
            throw new LiFengException(ResultCodeEnum.ONLY_MANAGER_CAN_OPERATE);
        }

        UserDO userDO=userDAO.getUserById(userId);
        if(newPassword == null){
            newPassword = "123456";
        }
        //生成新密码
        String finalPassword= DigestUtils.md5Hex(userDO.getTelno() + userDO.getSalt() + newPassword);
        int flag=userDAO.changeUserPassword(userId,finalPassword);
        if(flag == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }
    }

    @Override
    public List<RegisterResponseVO> getRegisterList(String status) {
        int statusInt;
        if(StringUtils.isEmpty(status)){
            statusInt = -1;
        }else{
            statusInt = Integer.parseInt(status);
        }
        return userDAO.getRegisterList(statusInt);
    }

    @Override
    public Set<UserListVO> getUserList() {
        return userDAO.getUserList();
    }

    @Override
    @Transactional
    public void userAudit(String userName, int status, int userTypeId, int roleId) {
        //非管理员无法进行审核
        if(! userDAO.isSystemManagerByUserId(tokenService.getUserId())){
            throw new LiFengException(ResultCodeEnum.ONLY_MANAGER_CAN_OPERATE);
        }

        RegisterDO registerDO=userDAO.getRegisterUserByPhone(userName);
        if(registerDO.getStatus().equals(UserAuditEnum.AUDIT_SUCCESS.getValue())){
            throw new LiFengException(ResultCodeEnum.USER_HAS_AUDITED);
        }
        int row=userDAO.changeUserStatus(userName,status);
        if(row == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }

        if(status == UserAuditEnum.AUDIT_SUCCESS.getValue()){
            if(userTypeId == 0){
                throw new LiFengException(ResultCodeEnum.PARAM_ERROR.getCode(),"请传递用户类型");
            }
            //添加用户信息
            UserDTO userDTO=new UserDTO();
            userDTO.setUserName(registerDO.getTelNo());
            userDTO.setPassword(registerDO.getPassword());
            userDTO.setSalt(registerDO.getSalt());
            userDTO.setRealName(registerDO.getRealName());
            userDTO.setType(userTypeId);
      System.out.println(userDTO);
             int userId=userDAO.insertUser(userDTO);
            if(userId == 0){
                throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
            }

            //分配用户角色
            systemAuthorityService.insertUserRole(userId,roleId);
        }


    }

    @Override
    public void addUser(UserAddVO userAddVO) {
        //非管理员无法进行添加用户
        if(! userDAO.isSystemManagerByUserId(tokenService.getUserId())){
            throw new LiFengException(ResultCodeEnum.ONLY_MANAGER_CAN_OPERATE);
        }

        UserDO userDO = userDAO.getUserByPhone(userAddVO.getUserName());
        if(userDO != null){
            throw new LiFengException(ResultCodeEnum.USER_HAS_REGISTERED);
        }

        //生成密码
        String salt= RandomUtil.generatorRandom(4,2);
        String finalPassword= DigestUtils.md5Hex(userAddVO.getUserName() + salt + userAddVO.getPassword());

        //添加用户信息
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName(userAddVO.getUserName());
        userDTO.setPassword(finalPassword);
        userDTO.setSalt(salt);
        userDTO.setRealName(userAddVO.getRealName());
        userDTO.setType(userAddVO.getUserTypeId());
        userDTO.setRemark(userAddVO.getRemark());
        System.out.println(userDTO);
        int userId=userDAO.insertUser(userDTO);
        if(userId == 0){
            throw new LiFengException(ResultCodeEnum.DATA_BASE_UPDATE_ERROR);
        }

        //分配用户角色
        systemAuthorityService.insertUserRole(userId,userAddVO.getRoleId());


    }

}
