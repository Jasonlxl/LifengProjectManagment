package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lxl")
@CrossOrigin
public class LxlByBusController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/get")
    public JSONObject getBusNo(@RequestBody JSONObject req){
        logger.info("查询车辆请求："+req);
        JSONObject resp = new JSONObject();

        String sql="SELECT busNo,busLic,location from lxl_bus where name=? ";
        Object[] params = new Object[] {req.getString("name")};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);

        String sql2="SELECT `value` from lxl_bus_config where `key`=? ";
        Object[] params2 = new Object[] {"time"};
        List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,params2);

        if(list == null || list.size() == 0){
            resp.put("code","-1");
            resp.put("msg","查询不到您的信息哟，请核对姓名后再次查询~~");
        }else{
            resp.put("code","0");
            resp.put("msg","您乘坐的车号为:"+list.get(0).get("busNo")+"号<br>车牌号为:"+list.get(0).get("busLic")+"<br>请您于"+list2.get(0).get("value")+"前,在"+list.get(0).get("location")+"乘车");
        }

        logger.info("返回数据："+resp);
        return resp;
    }
}
