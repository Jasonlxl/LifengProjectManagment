package com.sd.lifeng.dao;

import com.sd.lifeng.domain.SystemResourceDO;
import com.sd.lifeng.domain.SystemRolesDO;
import com.sd.lifeng.vo.auth.ResourceVO;
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
public class ResourceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Description 获取系统资源列表
     * @Auther bmr
     * @Date 2020/5/25 : 8:48 :51
     * @Return java.util.List<com.sd.lifeng.vo.auth.ResourceVO>
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
            resourceVO.setId(Integer.parseInt( map.get("id")+""));
            resourceVO.setResourceName(map.get("resource_name")+"");
            resourceVO.setResourceUrl(map.get("resource_url")+"");
            resourceVO.setParentId((Integer) map.get("parent_id"));
            resourceVO.setResourceType((Integer) map.get("resource_type"));
            resourceVOList .add(resourceVO);
        }
        return resourceVOList ;
    }


    /**
     * @Description 根据Id获取系统资源
     * @param resourceId 资源id
     * @Auther bmr
     * @Date 2020/5/25 : 8:37 :51
     * @Return
     */
    public SystemResourceDO getResourceById(int resourceId){
        String sql = "select * from pro_system_resource where id =?";
        Object[] params = {resourceId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        SystemResourceDO resourceDO=new SystemResourceDO();
        resourceDO.setId((Integer) list.get(0).get("id"));
        resourceDO.setResourceName((String)list.get(0).get("resource_name"));
        resourceDO.setResourceUrl((String)list.get(0).get("resource_url"));
        return resourceDO ;
    }

    /**
     * @description 添加系统资源
     * @param resourceDO 资源实例
     * @author bmr
     * @date 2020/5/25 : 8:54 :51
     * @return int
     */
    public int addResource(SystemResourceDO resourceDO){
        String sql="insert into pro_system_resource (resource_name,resource_url,resource_type) values (?,?,?)";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,resourceDO.getResourceName());
            preparedStatement.setString(2,resourceDO.getResourceUrl());
            preparedStatement.setInt(3,resourceDO.getResourceType());
        });
        System.out.println(rows);
        return rows;
    }

    /**
     * @description 更新系统资源信息
     * @param resourceDO
     * @author bmr
     * @date 2020/5/25 : 8:57 :51
     * @return int
     */
    public int updateResource(SystemResourceDO resourceDO){
        String sql="update pro_system_resource set resource_name = ?,resource_url = ?,resource_type = ? where id =?";
        int rows=jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1,resourceDO.getResourceName());
            preparedStatement.setString(2,resourceDO.getResourceUrl());
            preparedStatement.setInt(3,resourceDO.getResourceType());
            preparedStatement.setInt(4,resourceDO.getId());
        });
        System.out.println(rows);
        return rows;
    }

}
