package com.sd.lifeng.dao;

import com.sd.lifeng.domain.ProjectDO;
import com.sd.lifeng.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProjectDAO {
    //初始化日志记录器
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Description 根据项目名称，角色id查询项目表
     * @param projectName,roleId 项目名称，角色id
     * @Auther Jason
     * @Date 2020/5/23 : 22:59 :51
     * @Return com.sd.lifeng.domain.ProjectDAO
     */
    public boolean getRecordByProjectNameAndRoleId(String projectName,int roleId){
        String sql="select id from pro_project_details where project_name = ? and role_id = ?";
        Object[] params = new Object[] { projectName,roleId};

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size()>0){
            //存在记录
            return true;
        }
        return false;
    }

    public int addProjectRecord(ProjectDO projectDO) throws Exception{
        String sql = "insert into pro_project_details(projecthash,project_name,create_user,role_id,rolename,createdate,project_addr) values(?,?,?,?,?,now(),?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, projectDO.getProjectHash());
                ps.setString(2, projectDO.getProjectName());
                ps.setInt(3, projectDO.getCreateUser());
                ps.setInt(4, projectDO.getRoleId());
                ps.setString(5, projectDO.getRoleName());
                ps.setString(6, projectDO.getProjectAddr());
                return ps;
            }
        },keyHolder);
        return resRow;
    }
}
