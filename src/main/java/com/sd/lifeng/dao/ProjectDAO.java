package com.sd.lifeng.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sd.lifeng.domain.ProjectDO;
import org.junit.platform.commons.util.StringUtils;
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
     * @Return com.sd.lifeng.dao.ProjectDAO
     */
    public boolean getRecordByProjectNameAndRoleId(String projectName,int roleId){
        String sql="select id from pro_project_details where project_name = ? and role_id = ?";
        Object[] params = new Object[] { projectName,roleId};

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        //存在记录
        return list.size() > 0;
    }

    /**
     * @Description 根据项目串号判断是否可以编辑
     * @param projectHash 项目串号
     */
    public boolean checkProjectStatus(String projectHash){
        String sql="select status from pro_project_details where projecthash = ?";
        Object[] params = new Object[] { projectHash};

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        logger.info("check list :"+list);

        if(list == null || list.size() == 0){
            //不存在记录
            return true;
        }else{
            logger.info("status:"+list.get(0).get("status"));
            if((int)list.get(0).get("status") != 0){
                //项目不可编辑
                return true;
            }
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
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            String source_type = "";
            String source_name ;
            for(int i = 0; i<array.size(); i++){
                source_type = array.getJSONObject(i).getString("source_type");
                source_name = array.getJSONObject(i).getString("source_name");

                if(StringUtils.isBlank(source_type) || StringUtils.isBlank(source_name)){
                    throw new Exception("异常：检测到有静态资源为空");
                }

                pstmt.setString(1,projectHash);
                pstmt.setString(2,source_type);
                pstmt.setString(3,source_name);
                pstmt.addBatch();
            }

            res = pstmt.executeBatch();
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
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
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            String timeline_type;
            String timeline_name;
            for(int i = 0; i<array.size(); i++){
                timeline_type = array.getJSONObject(i).getString("timeline_type");
                timeline_name = array.getJSONObject(i).getString("timeline_name");

                if(StringUtils.isBlank(timeline_type) || StringUtils.isBlank(timeline_name)){
                    throw new Exception("异常：检测到有时间线资源为空");
                }
                pstmt.setString(1,projectHash);
                pstmt.setString(2,timeline_type);
                pstmt.setString(3,timeline_name);
                pstmt.setString(4,UUID.randomUUID().toString());
                pstmt.addBatch();
            }

            res = pstmt.executeBatch();
            conn.commit();
        }catch(SQLException e){
            conn.rollback();
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
     * @Description 查询pro_unit_part_model表--单位
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

    /**
     * @Description 插入pro_project_unit_part表
     * @Auther Jason
     */
    public int addProjectUnitPartBatch(String projectHash, JSONArray array) throws SQLException,Exception{
        String sql="insert into pro_project_unit_part(projecthash,unit_name,part_name) values(?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        int[] res;
        try{
            conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            String unit_name = "";
            String part_name = "";
            for(int i = 0; i<array.size(); i++){
                JSONObject unitObject = array.getJSONObject(i);
                unit_name = unitObject.getString("unit_name");

                if(StringUtils.isBlank(unit_name)){
                    throw new Exception("异常：检测到有单位名称为空");
                }

                JSONArray partArray = unitObject.getJSONArray("children");
                for(int j = 0; j<partArray.size(); j++){
                    JSONObject partObject = partArray.getJSONObject(j);
                    part_name = partObject.getString("part_name");

                    if(StringUtils.isBlank(part_name)){
                        throw new Exception("异常：检测到有分部名称为空");
                    }

                    pstmt.setString(1,projectHash);
                    pstmt.setString(2,unit_name);
                    pstmt.setString(3,part_name);
                    pstmt.addBatch();
                }
            }

            res = pstmt.executeBatch();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            conn.rollback();
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
     * @Description 查询pro_cent_model表
     * @Auther Jason
     */
    public List<Map<String,Object>> queryCent(){
        String sql="select * from pro_cent_model";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * @Description 查询pro_project_unit_part表
     * @Auther Jason
     */
    public List<Map<String,Object>> queryProjectPartList(String projectHash){
        String sql="select part_name from pro_project_unit_part where projecthash = ?";
        Object[] params = new Object[] {projectHash};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        return list;
    }

    /**
     * @Description 插入pro_project_cent_list表
     * @Auther Jason
     */
    public int addProjectPartCentBatch(String projectHash, JSONArray array) throws SQLException,Exception{
        String sql="insert into pro_project_cent_list(projecthash,part_name,cent_name,centhash,cent_type,createdate) values(?,?,?,?,?,now())";
        Connection conn = null;
        PreparedStatement pstmt = null;

        int[] res;
        try{
            conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            String part_name;
            String cent_name;
            String cent_type;
            String measurement;
            int total;
            int component;
            for(int i = 0; i<array.size(); i++){
                JSONObject partObject = array.getJSONObject(i);
                part_name = partObject.getString("part_name");

                if(StringUtils.isBlank(part_name)){
                    throw new Exception("异常：检测到有分部名称为空");
                }

                JSONArray centArray = partObject.getJSONArray("children");
                for(int j = 0; j<centArray.size(); j++){
                    JSONObject centObject = centArray.getJSONObject(j);
                    cent_name = centObject.getString("cent_name");

                    if(StringUtils.isBlank(cent_name)){
                        throw new Exception("异常：检测到有单元名称为空");
                    }

                    cent_type = centObject.getString("cent_type");

                    if("1".equals(cent_type)){
                        pstmt.setString(1,projectHash);
                        pstmt.setString(2,part_name);
                        pstmt.setString(3,cent_name);
                        pstmt.setString(4,UUID.randomUUID().toString());
                        pstmt.setString(5,cent_type);
                        pstmt.addBatch();
                    }else{
                        measurement = centObject.getString("measurement");
                        total = centObject.getInteger("total");
                        component = centObject.getInteger("component");

                        float total_float = total;
                        float component_float = component;
                        int row = (int)Math.ceil(total_float/component_float);
                        int head = 0;
                        String suffix;

                        for(int k = 1; k<=row; k++){
                            if((head+component)>=total){
                                suffix = "("+head+measurement+"--"+(total)+measurement+")";
                            }else{
                                suffix = "("+head+measurement+"--"+(head+component)+measurement+")";
                            }
                            pstmt.setString(1,projectHash);
                            pstmt.setString(2,part_name);
                            pstmt.setString(3,cent_name+suffix);
                            pstmt.setString(4,UUID.randomUUID().toString());
                            pstmt.setString(5,cent_type);
                            pstmt.addBatch();
                            head+=component;
                        }
                    }
                }
            }

            res = pstmt.executeBatch();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            conn.rollback();
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
     * @Description 查询pro_project_unit_part表--单位
     * @Auther Jason
     */
    public List<Map<String,Object>> queryUnitforProject(String projecthash){
        String sql="SELECT DISTINCT unit_name FROM pro_project_unit_part where projecthash=?";
        Object[] params = new Object[] {projecthash};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        return list;
    }

    /**
     * @Description 查询pro_project_unit_part表--分部
     * @Auther Jason
     */
    public List<Map<String,Object>> queryPartforProject(String projecthash,String unitName){
        String sql="SELECT part_name from pro_project_unit_part where projecthash=? and unit_name=?";
        Object[] params = new Object[] {projecthash,unitName};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        return list;
    }

    /**
     * @Description 联表查询pro_system_user_role表&pro_system_roles表，判断用户是否为管理员
     * @Auther Jason
     */
    public Boolean checkAdmin(String userId){
        String sql="SELECT system_manager from pro_system_user_role a RIGHT JOIN pro_system_roles b ON a.role_id=b.id WHERE user_id=?";
        Object[] params = new Object[] {Integer.valueOf(userId)};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list == null || list.size() == 0){
            return false;
        }else {
            if ((int)list.get(0).get("system_manager") != 1){
                return false;
            }
        }
        return true;
    }

    /**
     * @Description 查询pro_project_details表
     * @Auther Jason
     */
    public List<Map<String,Object>> queryEditProject(String userId, boolean flag){
        List<Map<String, Object>> list;
        if (flag){
            String sql="select * from pro_project_details where status=0";
            list = jdbcTemplate.queryForList(sql);
        }else{
            String sql="select * from pro_project_details where create_user=? and status=0";
            Object[] params = new Object[] {userId};
            list = jdbcTemplate.queryForList(sql,params);
        }
        return list;
    }
}
