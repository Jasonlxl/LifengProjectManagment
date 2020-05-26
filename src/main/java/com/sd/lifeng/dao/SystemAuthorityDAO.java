package com.sd.lifeng.dao;

import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.ResourceVO;
import com.sd.lifeng.vo.RoleVO;
import com.sd.lifeng.vo.UserListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bmr
 * @Classname SystemAuthorityDAO
 * @Description
 * @Date 2020/5/25 8:35:51
 */
@Repository
public class SystemAuthorityDAO {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Description 获取系统角色列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return java.util.List<com.sd.lifeng.vo.RoleVO>
     */
    public List<RoleVO> getRoleList(){
        String sql = "select * from pro_system_roles";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<RoleVO> roleVOList =new ArrayList<>();
        for(Map<String,Object> map:list){
            RoleVO roleVO=new RoleVO();
            roleVO.setId(Integer.parseInt((String) map.get("id")));
            roleVO.setRoleName(map.get("role_name").toString());
            roleVOList .add(roleVO);
        }
        return roleVOList ;
    }

    /**
     * @Description 获取系统资源列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:48 :51
     * @Return java.util.List<com.sd.lifeng.vo.ResourceVO>
     */
    public List<ResourceVO> getResourceList(){
        String sql = "select * from pro_system_resource";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<ResourceVO> resourceVOList =new ArrayList<>();
        for(Map<String,Object> map:list){
            ResourceVO resourceVO=new ResourceVO();
            resourceVO.setId(Integer.parseInt((String) map.get("id")));
            resourceVO.setResourceName(map.get("resource_name").toString());
            resourceVO.setResourceUrl(map.get("resource_url").toString());
            resourceVOList .add(resourceVO);
        }
        return resourceVOList ;
    }

    /**
     * @description 添加系统角色
     * @param rolesDO
     * @author bmr
     * @date 2020/5/25 : 8:54 :51
     * @return int
     */
    public int addRole(SystemRolesDO rolesDO){
        String sql="insert into pro_system_roles (rolen_name,system_manager) values (?,?)";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,rolesDO.getRoleName());
            preparedStatement.setInt(2,rolesDO.getSystemManager());
        });
        System.out.println(rows);
        return rows;
    }

    /**
     * @description 更新系统角色信息
     * @param rolesDO
     * @author bmr
     * @date 2020/5/25 : 8:57 :51
     * @return int
     */
    public int updateRole(SystemRolesDO rolesDO){
        String sql="update pro_system_roles set rolen_name = ?,system_manager = ? where id =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,rolesDO.getRoleName());
            preparedStatement.setInt(2,rolesDO.getSystemManager());
            preparedStatement.setInt(2,rolesDO.getId());
        });
        System.out.println(rows);
        return rows;
    }

    /**
     * @description 为用户分配角色
     * @param userId 用户id
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/26 : 17:16 :51
     * @return int
     */
    public int addUserRole(int userId, int roleId){
        String sql="insert into pro_system_user_role (user_id,role_id) values (?,?)";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,roleId);
        });
        logger.info("【为用户分配角色】插入影响行数:{}",rows);
        return rows;
    }

    /**
     * @description 为角色分配资源
     * @param roleId 角色id
     * @param resourceId 资源id
     * @author bmr
     * @date 2020/5/26 : 17:21 :51 
     * @return int
     */
    public int addRoleResource(int roleId, int resourceId){
        String sql="insert into pro_system_role_resource (role_id,resource_id) values (?,?)";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,roleId);
            preparedStatement.setInt(2,resourceId);
        });
        logger.info("【为角色分配资源】插入影响行数:{}",rows);
        return rows;
    }
}
