package com.sd.lifeng.serviceImpl;

import com.sd.lifeng.service.IUserCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    /*
    密码检查
     */
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
}
