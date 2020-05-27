package com.sd.lifeng.dao;

import com.sd.lifeng.domain.RegisterDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.dto.UserDTO;
import com.sd.lifeng.enums.UserAuditEnum;
import com.sd.lifeng.vo.user.RegisterResponseVO;
import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import com.sd.lifeng.vo.user.UserListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        UserDO userDO=new UserDO();
        userDO.setId((Integer) list.get(0).get("id"));
        userDO.setTelno((String) list.get(0).get("telno"));
        userDO.setPasswd((String) list.get(0).get("passwd"));
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
     * @Description 获取用户注册列表
     * @param status 审核状态 不填默认查询全部
     * @Auther bmr
     * @Date 2020/5/24 : 8:48 :51 
     * @Return java.util.List<com.sd.lifeng.vo.user.RegisterResponseVO>
     */
    public List<RegisterResponseVO> getRegisterList(int status){
        String sql;
        if(status == -1){
            //没有查询条件  查询所有的注册列表
            sql="select * from pro_register";
        }else{
            sql="select * from pro_register where status = "+status;
        }
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<RegisterResponseVO> registerResponseVOS =new ArrayList<>();
        for(Map<String,Object> map:list){
            RegisterResponseVO registerResponseVO=new RegisterResponseVO();
            registerResponseVO.setTelNo(map.get("telno").toString());
            registerResponseVO.setRealName(map.get("real_name").toString());
            registerResponseVO.setCreateDate(map.get("createdate").toString());
            registerResponseVO.setStatus(Integer.parseInt(map.get("status").toString()));
            String statusRemark= UserAuditEnum.getRemark(Integer.parseInt(map.get("status").toString()));
            registerResponseVO.setStatusDescription(statusRemark);
            registerResponseVOS .add(registerResponseVO);
        }
        return registerResponseVOS ;
    }

    /**
     * @Description 往register表中插入注册用户信息
     * @param registerDO  注册用户信息
     * @Auther bmr
     * @Date 2020/5/22 : 8:27 :51
     * @Return void
     */
    public int addRegister(RegisterDO registerDO){
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
        String sql="insert into pro_user (telno,passwd,salt,realname,user_type_id,remark) values (?,?,?,?,?,?)";

    }

    /**
     * @Description 获取用户列表
     * @Auther bmr
     * @Date 2020/5/24 : 8:49 :51 
     * @Return java.util.List<com.sd.lifeng.vo.user.UserListVO>
     */
    public Set<UserListVO> getUserList(){
    //        String sql = "select u.*,r.rolename,types.type,types.typename from pro_user u left
    // join pro_system_roles r on r.id=u.roleid left join pro_types types on
    // types.id=u.user_type_id";

        String sql = "SELECT u.*,types.type,types.typename,ro.id as role_id,ro.role_name,re.id as resource_id,re.resource_name,re.resource_url FROM pro_users u LEFT JOIN pro_types types ON types.id=u.user_type_id LEFT JOIN pro_system_user_role ur ON  ur.user_id=u.id LEFT JOIN pro_system_roles ro ON ro.id=ur.role_id LEFT JOIN pro_system_role_resource rr ON rr.role_id=ro.id LEFT JOIN pro_system_resource re ON re.id=rr.resource_id";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        System.out.println(list);
        Set<UserListVO> userListVOList =new HashSet<>();
        for(Map<String,Object> map:list){
            UserListVO userListVO=new UserListVO();
            userListVO.setUserId(Integer.parseInt(map.get("id").toString()));
            userListVO.setTelNo(map.get("telno").toString());
            userListVO.setRealName(map.get("realname").toString());
            userListVO.setCreateDate(map.get("createdate").toString());
            userListVO.setType(Integer.parseInt(map.get("type").toString()));
            userListVO.setTypeName(map.get("typename").toString());
            userListVOList.add(userListVO);
        }
        for (UserListVO userListVO : userListVOList){
            for(Map<String,Object> map:list){
                int userId=Integer.parseInt(map.get("id").toString());
                if(userListVO.getUserId() == userId){
                    RoleVO roleVO=new RoleVO();
                    roleVO.setId(Integer.parseInt(map.get("role_id").toString()));
                    roleVO.setRoleName(map.get("role_name").toString());
                    userListVO.getRoleList().add(roleVO);

                    ResourceVO resourceVO=new ResourceVO();
                    resourceVO.setId(Integer.parseInt(map.get("resource_id").toString()));
                    resourceVO.setResourceName(map.get("resource_name").toString());
                    resourceVO.setResourceUrl(map.get("resource_url").toString());
                    userListVO.getResourceList().add(resourceVO);
                }
            }
        }
        return userListVOList ;
    }

    /**
     * @Description 更改用户密码
     * @param userId 用户id
     * @param password 新密码
     * @Auther bmr
     * @Date 2020/5/24 : 9:08 :51
     * @Return int
     */
    public int changeUserPassword(int userId,String password){
        String sql="update pro_user set password = ? where id = ?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,password);
            preparedStatement.setInt(2,userId);
        });
        return rows;
    }

    /**
     * @Description 更改用户审核状态
     * @param phone 用户手机号
     * @param status 审核状态
     * @Auther bmr
     * @Date 2020/5/25 : 8:30 :51
     * @Return int
     */
    public int changeUserStatus(String phone, int status){
        String sql="update pro_register set status =? where id = ?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,phone);
            preparedStatement.setInt(2,status);
        });
        return rows;
    }



}
