package com.sd.lifeng.dao;

import com.sd.lifeng.domain.RegisterDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户名密码获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public UserDO getUserByNamePassword(String userName,String password){
        String sql="select * from pro_users where  telno=? and passwd=? ";
        Object[] params = new Object[] { userName, password};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
//        System.out.println(list);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        UserDO userDO=new UserDO();
        userDO.setId((Integer) list.get(0).get("id"));
        userDO.setTelno((String) list.get(0).get("telno"));
        userDO.setPasswd((String) list.get(0).get("passwd"));
//        System.out.println(userDO);
        return userDO;
    }

   /**
    * @Description 根据用户id获取用户信息
    * @param userId 用户id
    * @Auther bmr
    * @Date 2020/5/19 : 8:43 :51
    * @Return com.sd.lifeng.domain.UserDO
    */
    public UserDO getUserById(int userId){
        String sql="select * from pro_user where id = ?";
        Object[] params = new Object[] { userId};
        return jdbcTemplate.queryForObject(sql,params,UserDO.class);
    }

    /**
     * @Description 根据手机号获取用户信息
     * @param phone 手机号
     * @Auther bmr
     * @Date 2020/5/19 : 8:43 :51
     * @Return com.sd.lifeng.domain.UserDO
     */
    public UserDO getUserByPhone(String phone){
        String sql="select * from pro_user where telno = ?";
        Object[] params = new Object[] { phone};
        UserDO userDO =jdbcTemplate.queryForObject(sql,params,UserDO.class);
        return userDO;
    }

    /**
     * @Description 根据手机号获取用户注册信息
     * @param phone 手机号
     * @Auther bmr
     * @Date 2020/5/19 : 8:43 :51
     * @Return com.sd.lifeng.domain.UserDO
     */
    public RegisterDO getRegisterUserByPhone(String phone){
        String sql="select * from pro_register where telno = ?";
        Object[] params = new Object[] { phone};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        RegisterDO registerDO=new RegisterDO();
        registerDO.setId((Integer) list.get(0).get("id"));
        registerDO.setTelNo((String) list.get(0).get("telno"));
        registerDO.setPassword((String) list.get(0).get("passwd"));
        return registerDO;
    }

    /**
     * @Description 往register表中插入注册用户信息
     * @param registerDO  注册用户信息
     * @Auther bmr
     * @Date 2020/5/22 : 8:27 :51
     * @Return void
     */
    public int insertRegister(RegisterDO registerDO){
        String sql="insert into pro_register (telno,passwd,salt,status,remark,real_name) values (?,?,?,?,?,?)";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,registerDO.getTelNo());
            preparedStatement.setString(2,registerDO.getPassword());
            preparedStatement.setString(3,registerDO.getSalt());
            preparedStatement.setInt(4,registerDO.getStatus());
            preparedStatement.setString(5,registerDO.getRemark());
            preparedStatement.setString(6,registerDO.getRealName());
        });
    System.out.println(rows);
        return rows;
    }

    public void insertUser(UserDTO userDTO){
        String sql="insert into pro_user (telno,passwd,salt,realname,type,remark) values (?,?,?,?,?,?)";

    }

}
