package com.sd.lifeng.dao;

import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.vo.user.LoginResponseVO;
import com.sd.lifeng.vo.user.UserListVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author bmr
 * @classname UserDAOTest
 * @description
 * @date 2020/5/26 16:00:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
   public void getUserListTest(){
        Set<UserListVO> userListVOList =userDAO.getUserList();
    System.out.println(JSONObject.toJSONString(userListVOList));
    }

    @Test
    public void getUserDetailById(){
        LoginResponseVO responseVO=userDAO.getUserDetailById(1);
    System.out.println(responseVO);
    System.out.println(JSONObject.toJSONString(responseVO));
    }
}