package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.config.JwtConfig;
import com.sd.lifeng.dao.UserDAO;
import com.sd.lifeng.domain.RegisterDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.enums.ResultCodeEnum;
import com.sd.lifeng.enums.UserAuditEnum;
import com.sd.lifeng.exception.LiFengException;
import com.sd.lifeng.service.ITokenService;
import com.sd.lifeng.service.IUserCategoryService;
import com.sd.lifeng.util.RandomUtil;
import com.sd.lifeng.util.TokenUtil;
import com.sd.lifeng.vo.RegisterResponseVO;
import com.sd.lifeng.vo.UserListVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/5/18.
 */
@Service
public class UserCategoryServiceImpl implements IUserCategoryService {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //注入jdbc
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private JwtConfig jwtConfig;


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
        int count=userDAO.insertRegister(dto);
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
    public Map login(String userName,String password){
        UserDO userDO=userDAO.getUserByPhone(userName);
        if(userDO == null){
            throw new LiFengException(ResultCodeEnum.USER_NOT_EXIST);
        }
        String finalPassword=DigestUtils.md5Hex(userDO.getTelno() + userDO.getSalt() + password);
        UserDO userDO1=userDAO.getUserByNamePassword(userName,finalPassword);
        if(userDO1 == null){
            throw new LiFengException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生成jwt
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowTime=LocalDateTime.now();
        String expireTime= df.format(nowTime.plusDays(1L));
        String token= TokenUtil.createToken(userDO.getId().toString(),expireTime, jwtConfig.getKey());

        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        return map;
    }



    /*
    密码检查
     */
    @Override
    public boolean passwdCheck(String passwd){
        String sql = "select passwd from pro_users where id=1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        logger.info("list:"+ list);

        if(passwd.equals(list.get(0).get("passwd"))){
            return true;
        }else{
            return false;
        }

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
    public void changePassword(String newPassword) {
        int userId=tokenService.getUserId();
        UserDO userDO=userDAO.getUserById(userId);
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
    public List<UserListVO> getUserList() {
        return userDAO.getUserList();
    }
}
