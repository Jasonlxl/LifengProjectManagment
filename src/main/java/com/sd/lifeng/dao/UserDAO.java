package com.sd.lifeng.dao;

import com.sd.lifeng.domain.RegisterDO;
import com.sd.lifeng.domain.SystemResourceDO;
import com.sd.lifeng.domain.UserDO;
import com.sd.lifeng.dto.UserDTO;
import com.sd.lifeng.enums.UserAuditEnum;
import com.sd.lifeng.util.ResourceTreeUtils;
import com.sd.lifeng.vo.auth.ResourceTreeVO;
import com.sd.lifeng.vo.user.LoginResponseVO;
import com.sd.lifeng.vo.user.RegisterResponseVO;
import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import com.sd.lifeng.vo.user.UserListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class UserDAO {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SystemAuthorityDAO authorityDAO;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleDao roleDao;

//    @Autowired
//    private UserDAO userDAO;

    /**
     * @description 获取传递过来的用户是否为系统管理员
     * @author bmr
     * @param userId 用户id
     * @date 2020/5/28 : 18:26 :51
     * @return boolean
     */
    public boolean isSystemManagerByUserId(Integer userId) {
        boolean flag = false;
        List<RoleVO> roleVOList= authorityDAO.getRolesByUserId(userId);
        if(CollectionUtils.isEmpty(roleVOList)){
            return false;
        }

        for(RoleVO roleVO : roleVOList){
            if(roleVO.getSystemManager() == 1){
                flag = true;
                break;
            }
        }
        return flag;
    }

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
        String sql="select * from pro_users where id = ?";
        Object[] params = new Object[] { userId};

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        UserDO userDO=new UserDO();
        userDO.setId((Integer) list.get(0).get("id"));
        userDO.setTelno((String) list.get(0).get("telno"));
        userDO.setPasswd((String) list.get(0).get("passwd"));
        userDO.setSalt((String) list.get(0).get("salt"));
        return userDO;
    }

    /**
     * @Description 根据手机号获取用户信息
     * @param phone 手机号
     * @Auther bmr
     * @Date 2020/5/19 : 8:43 :51
     * @Return com.sd.lifeng.domain.UserDO
     */
    public UserDO getUserByPhone(String phone){
        String sql="select * from pro_users where telno = ?";
        Object[] params = new Object[] { phone};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        UserDO userDO=new UserDO();
        userDO.setId((Integer) list.get(0).get("id"));
        userDO.setTelno((String) list.get(0).get("telno"));
        userDO.setPasswd((String) list.get(0).get("passwd"));
        userDO.setSalt((String) list.get(0).get("salt"));
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
        registerDO.setRealName((String) list.get(0).get("real_name"));
        registerDO.setSalt((String) list.get(0).get("salt"));
        registerDO.setStatus((Integer) list.get(0).get("status"));
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
    System.out.println(list);
        List<RegisterResponseVO> registerResponseVOS =new ArrayList<>();
        for(Map<String,Object> map:list){
            RegisterResponseVO registerResponseVO=new RegisterResponseVO();
            registerResponseVO.setTelNo(map.get("telno").toString());
            registerResponseVO.setRealName(map.get("real_name").toString());
            registerResponseVO.setCreateDate(map.get("createdate").toString());
            registerResponseVO.setStatus(Integer.parseInt(map.get("status").toString()));
            String statusRemark= UserAuditEnum.getRemark(Integer.parseInt(map.get("status").toString()));
            registerResponseVO.setStatusDescription(statusRemark);
      System.out.println(registerResponseVO);
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

    public int insertUser(UserDTO userDTO){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql="insert into pro_users (telno,passwd,salt,realname,user_type_id,remark) values (?,?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,userDTO.getUserName());
            ps.setString(2,userDTO.getPassword());
            ps.setString(3,userDTO.getSalt());
            ps.setString(4,userDTO.getRealName());
            ps.setInt(5,userDTO.getType());
            ps.setString(6,userDTO.getRemark());
            return ps;
        },keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    /**
     * @Description 获取用户列表
     * @Auther bmr
     * @Date 2020/5/24 : 8:49 :51
     * @Return java.util.List<com.sd.lifeng.vo.user.UserListVO>
     */
    public Set<UserListVO> getUserList(){

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
            //如果是管理员角色，那么就需要查询所有的资源列表
            boolean isManage=this.isSystemManagerByUserId(userListVO.getUserId());

            for(Map<String,Object> map:list){
                int userId=Integer.parseInt(map.get("id").toString());

                if(userListVO.getUserId() == userId){
                    if(map.get("role_id") != null){
                        RoleVO roleVO=new RoleVO();
                        roleVO.setId(Integer.parseInt(map.get("role_id").toString()));
                        roleVO.setRoleName(map.get("role_name").toString());
                        userListVO.getRoleList().add(roleVO);

                        if(!isManage){
                            //非管理员用户，添加拥有的角色的对应资源，管理员不用添加，最后统一返回全部资源
                            ResourceVO resourceVO=new ResourceVO();
                            resourceVO.setId(Integer.parseInt(map.get("resource_id").toString()));
                            resourceVO.setResourceName(map.get("resource_name").toString());
                            resourceVO.setResourceUrl(map.get("resource_url").toString());
                            userListVO.getResourceList().add(resourceVO);
                        }

                    }

                }
            }

            if(isManage){
                //管理员返回所有资源
                List<ResourceVO> resourceList=resourceDao.getResourceList();
                for(ResourceVO resourceVO:resourceList){
                    userListVO.getResourceList().add(resourceVO);
                }
            }
        }
        return userListVOList ;
    }

    /**
     * @Description 获取用户详情  包含角色和可查看资源
     * @param userId 用户id
     * @Auther bmr
     * @Date 2020/5/24 : 8:49 :51
     * @Return java.util.List<com.sd.lifeng.vo.user.UserListVO>
     */
    public LoginResponseVO getUserDetailById(int userId){

        String sql = "SELECT u.*,types.type,types.typename,ro.id as role_id,ro.role_name,ro.system_manager,re.id as resource_id,re.resource_name,re.resource_url,re.parent_id,re.icon,re.resource_type,re.resource_order FROM pro_users u LEFT JOIN pro_types types ON types.id=u.user_type_id LEFT JOIN pro_system_user_role ur ON  ur.user_id=u.id LEFT JOIN pro_system_roles ro ON ro.id=ur.role_id LEFT JOIN pro_system_role_resource rr ON rr.role_id=ro.id LEFT JOIN pro_system_resource re ON re.id=rr.resource_id where u.id = ?";
        Object[] params = new Object[] { userId };
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        System.out.println(list);
        Set<LoginResponseVO> responseVOList = new HashSet<>();
        for(Map<String,Object> map:list){
            LoginResponseVO responseVO=new LoginResponseVO();
            responseVO.setUserId(Integer.parseInt(map.get("id").toString()));
            responseVO.setUserName(map.get("telno").toString());
            responseVO.setRealName(map.get("realname").toString());
            responseVO.setCreateTime(map.get("createdate").toString());
            responseVO.setUserType(Integer.parseInt(map.get("type").toString()));
            responseVO.setUserTypeName(map.get("typename").toString());
            responseVOList.add(responseVO);
        }



        Set<ResourceTreeVO> resourceTreeVOSet = new HashSet<>();
        for (LoginResponseVO responseVO : responseVOList){

            for(Map<String,Object> map:list){
                int id=Integer.parseInt(map.get("id").toString());
                if(responseVO.getUserId() == id){

                    if(map.get("role_id") != null){
                        RoleVO roleVO=new RoleVO();
                        roleVO.setId(Integer.parseInt(map.get("role_id").toString()));
                        roleVO.setRoleName(map.get("role_name").toString());
                        roleVO.setSystemManager(Integer.parseInt(map.get("system_manager").toString()));
                        responseVO.getRoleVOList().add(roleVO);
                    }

                    if(map.get("resource_id") != null){
                        ResourceTreeVO resourceTreeVO=new ResourceTreeVO();
                        resourceTreeVO.setId(Integer.parseInt(map.get("resource_id")+""));
                        resourceTreeVO.setName(map.get("resource_name")+"");
                        resourceTreeVO.setPath(map.get("resource_url")+"");
                        resourceTreeVO.setResourceType(Integer.parseInt(map.get("resource_type")+""));
                        resourceTreeVO.setParentId(Integer.parseInt(map.get("parent_id")+""));
                        resourceTreeVO.setIcon(map.get("icon")+"");
                        resourceTreeVO.setResourceOrder((Integer) map.get("resource_order"));
                        resourceTreeVOSet.add(resourceTreeVO);


                    }

                }
            }
        }

        //构建资源树
        LoginResponseVO loginResponseVO =responseVOList.iterator().next();
        if(isSystemManagerByUserId(loginResponseVO.getUserId())){
            //如果是系统管理员，返回所有的资源树
            List<ResourceVO> resourceVOList = resourceDao.getResourceList();
            resourceTreeVOSet.clear();
            resourceVOList.forEach(resourceVO -> {
                ResourceTreeVO resourceTreeVO =new ResourceTreeVO();
                resourceTreeVO.setId(resourceVO.getId());
                resourceTreeVO.setName(resourceVO.getResourceName());
                resourceTreeVO.setIcon(resourceVO.getIcon());
                resourceTreeVO.setParentId(resourceVO.getParentId());
                resourceTreeVO.setPath(resourceVO.getResourceUrl());
                resourceTreeVO.setResourceType(resourceVO.getResourceType());
                resourceTreeVO.setResourceOrder(resourceVO.getResourceOrder());
                resourceTreeVOSet.add(resourceTreeVO);
            });

        }

        if(resourceTreeVOSet.size() != 0){
            resourceTreeVOSet.forEach(resourceTreeVO -> {
                if(ResourceTreeUtils.isRootElement(resourceTreeVO)){
                    Set<ResourceTreeVO> voSet = ResourceTreeUtils.getChildNodes(resourceTreeVO.getId(),resourceTreeVOSet);
                    if(voSet != null){
                        resourceTreeVO.setChildren(voSet);
                    }
                    loginResponseVO.getResourceVOList().add(resourceTreeVO);
                }
            });
        }



        return loginResponseVO ;
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
        String sql="update pro_users set passwd =? where id =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,password);
            preparedStatement.setInt(2,userId);
        });
        return rows;
    }

    /**
     * @Description 更改用户手机设备token值
     * @param userId 用户id
     * @param clientId 设备token值
     * @Auther bmr
     * @Date 2020/7/24 : 9:08 :51
     * @Return int
     */
    public int changeDeviceToken(int userId,String clientId){
        String sql="update pro_users set clientid =? where id =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,clientId);
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
        String sql="update pro_register set status =? where telno =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,status);
            preparedStatement.setString(2,phone);
        });
        return rows;
    }


}
