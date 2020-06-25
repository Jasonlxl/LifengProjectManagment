package com.sd.lifeng.dao;

import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.auth.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Description 获取系统角色列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return java.util.List<com.sd.lifeng.vo.auth.RoleVO>
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
            roleVO.setId(Integer.parseInt( map.get("id")+""));
            roleVO.setRoleName(map.get("role_name")+"");
            roleVO.setSystemManager((Integer) map.get("system_manager"));
            roleVOList .add(roleVO);
        }
        return roleVOList ;
    }

    /**
     * @Description 根据Id获取系统角色
     * @param roleId 角色id
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return SystemRolesDO
     */
    public SystemRolesDO getRoleById(int roleId){
        String sql = "select * from pro_system_roles where id =?";
        Object[] params = {roleId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        SystemRolesDO rolesDO=new SystemRolesDO();
        rolesDO.setId((Integer) list.get(0).get("id"));
        rolesDO.setRoleName((String)list.get(0).get("role_name"));
        rolesDO.setSystemManager((Integer) list.get(0).get("system_manager"));
        return rolesDO ;
    }

    /**
     * @description 添加系统角色
     * @param rolesDO
     * @author bmr
     * @date 2020/5/25 : 8:54 :51
     * @return int
     */
    public int addRole(SystemRolesDO rolesDO){
        String sql="insert into pro_system_roles (role_name,system_manager) values (?,?)";
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
        String sql="update pro_system_roles set role_name = ?,system_manager = ? where id =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,rolesDO.getRoleName());
            preparedStatement.setInt(2,rolesDO.getSystemManager());
            preparedStatement.setInt(3,rolesDO.getId());
        });
        System.out.println(rows);
        return rows;
    }

    /**
     * @description 删除系统角色信息
     * @param roleId 角色id
     * @author bmr
     * @date 2020/5/25 : 8:57 :51
     * @return int
     */
    public int deleteRole(int roleId){
        String sql="delete from pro_system_roles  where id = ?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setInt(1,roleId);
        });
        System.out.println(rows);
        return rows;
    }



}
