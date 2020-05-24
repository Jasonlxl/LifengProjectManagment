package com.sd.lifeng.dao;

import com.sd.lifeng.vo.UserListVO;
import com.sd.lifeng.vo.UserTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserTypeDAO
 * @Description
 * @Author bmr
 * @Date 2020/5/24 8:39:51
 */
@Repository
public class UserTypeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<UserTypeVO> getUserTypeList(){
        String sql = "select * from  pro_types ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<UserTypeVO> userTypeVOS =new ArrayList<>();
        for(Map<String,Object> map:list){
            UserTypeVO typeVO=new UserTypeVO();
            typeVO.setType(Integer.parseInt((String) map.get("type")));
            typeVO.setTypeName((String) map.get("typename"));
            userTypeVOS .add(typeVO);
        }
        return userTypeVOS ;
    }
}
