package com.sd.lifeng.dao;

import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    /**
     * @description 为角色分配资源  批量
     * @param roleId 角色id
     * @param resourceIdList 资源id集合
     * @author bmr
     * @date 2020/5/26 : 17:21 :51
     * @return int
     */
    public int editRoleResourceBatch(int roleId, List<Integer> resourceIdList) throws Exception{
        int row;
        String delSql = "delete from pro_system_role_resource where role_id = ?";
        row = jdbcTemplate.update(delSql, preparedStatement -> {
            preparedStatement.setInt(1,roleId);
        });

        if(resourceIdList.size() != 0){
            String sql="insert into pro_system_role_resource (role_id,resource_id) values (?,?)";
            Connection conn = null;
            PreparedStatement pstmt = null;

            int[] res = new int[0];
            try{
                conn = jdbcTemplate.getDataSource().getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql);


                for (int resourceId : resourceIdList) {
                    pstmt.setInt(1, roleId);
                    pstmt.setInt(2, resourceId);
                    pstmt.addBatch();
                }

                res = pstmt.executeBatch();
                conn.commit();
            }catch(Exception e){
                assert conn != null;
                conn.rollback();
                e.printStackTrace();
                logger.error(e.getMessage());
            } finally {
                if(conn != null){
                    conn.close();
                }
                if(pstmt != null){
                    pstmt.close();
                }
            }
            row = res.length;
        }

        return row;
    }

    /**
     * @description 移除用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/26 : 17:21 :51
     * @return int
     */
    public int removeUserRole(int userId, int roleId){
        String sql="DELETE FROM pro_system_user_role where user_id=? and role_id=?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,roleId);
        });
        logger.info("【移除用户角色】影响行数:{}",rows);
        return rows;
    }

    /**
     * @description 根据用户id移除用户角色
     * @param userId 用户id
     * @author bmr
     * @date 2020/5/26 : 17:21 :51
     * @return int
     */
    public int removeUserRoleByUserId(int userId){
        String sql="DELETE FROM pro_system_user_role where user_id=?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,userId);
        });
        logger.info("【移除用户所分配的所有角色】影响行数:{}",rows);
        return rows;
    }



    /**
     * @Description 根据userId和roleId获取用户分配的角色
     * @param userId 用户id
     * @param roleId 角色id
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return
     */
    public boolean getUserRoleByUserIdAndRoleId(int userId, int roleId){
        String sql = "select * from pro_system_user_role where user_id =? and role_id=?";
        Object[] params = {userId,roleId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }
        return true ;
    }

    /**
     * 根据用户id获取用户角色列表
     * @param userId 用户id
     * @return
     */
    public List<RoleVO> getRolesByUserId(int userId){
        String sql = "select r.* from pro_system_user_role ur INNER JOIN pro_system_roles r ON ur.role_id=r.id where ur.user_id =? group by r.id";
        Object[] params = {userId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        List<RoleVO> roleVOList = new ArrayList<>();
        for (Map<String, Object> map : list){
            RoleVO roleVO = new RoleVO();
            roleVO.setId((Integer) map.get("id"));
            roleVO.setRoleName((String)map.get("role_name"));
            roleVO.setSystemManager((Integer) map.get("system_manager"));
            roleVOList.add(roleVO);
        }

        return roleVOList ;
    }

    /**
     * 根据角色id获取资源列表
     * @param roleId  角色id
     * @return
     */
    public List<ResourceVO> getResourcesByRoleId(int roleId){
        String sql = "select r.* from pro_system_role_resource rr INNER JOIN pro_system_resource r ON rr.resource_id=r.id where rr.role_id =? group by r.id";
        Object[] params = {roleId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        List<ResourceVO> resourceVOList = new ArrayList<>();
        for (Map<String, Object> map : list){
            ResourceVO resourceVO=new ResourceVO();
            resourceVO.setId(Integer.parseInt( map.get("id").toString()));
            resourceVO.setResourceName(map.get("resource_name").toString());
            resourceVO.setResourceUrl(map.get("resource_url").toString());
            resourceVOList .add(resourceVO);
        }

        return resourceVOList ;
    }

    /**
     * @Description 根据roleId和resourceId获取角色所拥有的资源
     * @param roleId 角色id
     * @param resourceId 资源id
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return
     */
    public boolean getRoleResourceByRoleIdAndResourceId(int roleId, int resourceId){
        String sql = "select * from pro_system_role_resource where role_id =? and resource_id=? ";
        Object[] params = {roleId,resourceId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }
        return true ;
    }

    /**
     * @description 移除角色资源
     * @param roleId 角色id
     * @param resourceId  资源id
     * @author bmr
     * @date 2020/5/26 : 17:21 :51
     * @return int
     */
    public int removeRoleResource(int roleId, int resourceId){
        String sql="DELETE FROM pro_system_role_resource where role_id=? and resource_id=?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,roleId);
            preparedStatement.setInt(2,resourceId);
        });
        logger.info("【移除角色资源】影响行数:{}",rows);
        return rows;
    }

    /**
     * @description 移除角色资源  批量
     * @param roleId 角色id
     * @param resourceIdList  资源id集合
     * @author bmr
     * @date 2020/5/26 : 17:21 :51
     * @return int
     */
    public int removeRoleResourceBatch(int roleId, List<Integer> resourceIdList) throws SQLException {
        String sql="DELETE FROM pro_system_role_resource where role_id=? and resource_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int[] res = new int[0];
        try{
            conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);


            for (int resourceId : resourceIdList) {
                pstmt.setInt(1, roleId);
                pstmt.setInt(2, resourceId);
                pstmt.addBatch();
            }

            res = pstmt.executeBatch();
            conn.commit();
        }catch(Exception e){
            assert conn != null;
            conn.rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if(conn != null){
                conn.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
        }
        return res.length;
    }
}
