package com.sd.lifeng.dao;

import com.sd.lifeng.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        System.out.println(list);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        UserDO userDO=new UserDO();
        userDO.setId((Integer) list.get(0).get("id"));
        userDO.setTelno((String) list.get(0).get("telno"));
        userDO.setPasswd((String) list.get(0).get("password"));
        System.out.println(userDO);
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
}
