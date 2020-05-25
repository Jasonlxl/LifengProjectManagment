package com.sd.lifeng.dao;

import com.alibaba.fastjson.JSONArray;
import com.sd.lifeng.domain.ProjectDO;
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

    /**
     * @Description 新项目插入pro_project_details表
     * @Auther Jason
     */
    public int addProjectRecord(ProjectDO projectDO) throws Exception{
        String sql = "insert into pro_project_details(projecthash,project_name,create_user,role_id,rolename,createdate,project_addr) values(?,?,?,?,?,now(),?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, projectDO.getProjectHash());
            ps.setString(2, projectDO.getProjectName());
            ps.setInt(3, projectDO.getCreateUser());
            ps.setInt(4, projectDO.getRoleId());
            ps.setString(5, projectDO.getRoleName());
            ps.setString(6, projectDO.getProjectAddr());
            return ps;
        },keyHolder);
        return resRow;
    }

    /**
     * @Description 查询pro_source_model表
     * @Auther Jason
     */
    public List<Map<String,Object>> querySource(){
        String sql="select * from pro_source_model";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * @Description 插入pro_project_source表
     * @Auther Jason
     */
    public int addProjectSourceBatch(String projectHash, JSONArray array) throws SQLException,Exception{
        String sql="insert into pro_project_source(projecthash,source_type,source_name) values(?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        int[] res;
        try{
            conn = jdbcTemplate.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);

            for(int i = 0; i<array.size(); i++){
                pstmt.setString(1,projectHash);
                pstmt.setString(2,array.getJSONObject(i).getString("source_type"));
                pstmt.setString(3,array.getJSONObject(i).getString("source_name"));
                pstmt.addBatch();
            }

            res = pstmt.executeBatch();
        }catch(SQLException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new SQLException(e);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception(e);
        }finally {
            if(conn != null){
                conn.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
        }
        return res.length;
    }

    /**
     * @Description 查询pro_timeline_model表
     * @Auther Jason
     */
    public List<Map<String,Object>> queryTimeline(){
        String sql="select * from pro_timeline_model";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * @Description 插入pro_project_timeline表
     * @Auther Jason
     */
    public int addProjectTimelineBatch(String projectHash, JSONArray array) throws SQLException,Exception{
        String sql="insert into pro_project_timeline(projecthash,timeline_type,timeline_name,timelinehash) values(?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        int[] res;
        try{
            conn = jdbcTemplate.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sql);

            for(int i = 0; i<array.size(); i++){
                pstmt.setString(1,projectHash);
                pstmt.setString(2,array.getJSONObject(i).getString("timeline_type"));
                pstmt.setString(3,array.getJSONObject(i).getString("timeline_name"));
                pstmt.setString(4,UUID.randomUUID().toString());
                pstmt.addBatch();
            }

            res = pstmt.executeBatch();
        }catch(SQLException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new SQLException(e);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception(e);
        }finally {
            if(conn != null){
                conn.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
        }
        return res.length;
    }

    /**
     * @Description 查询pro_unit_part_model表--单元
     * @Auther Jason
     */
    public List<Map<String,Object>> queryUnit(){
        String sql="SELECT DISTINCT unit_name FROM pro_unit_part_model";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * @Description 查询pro_unit_part_model表--分部
     * @Auther Jason
     */
    public List<Map<String,Object>> queryPart(String unitName){
        String sql="SELECT part_name from pro_unit_part_model where unit_name=?";
        Object[] params = new Object[] {unitName};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        return list;
    }
}
