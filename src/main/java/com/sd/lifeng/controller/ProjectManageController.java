package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lifeng/projectctl")
@CrossOrigin
public class ProjectManageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/addnewpro")
    public String register(@RequestBody JSONObject req){
        logger.info("register send into msg :"+req);

        String sql = "select * from pro_users where id=1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        logger.info("list:"+ list);

        JSONObject response = new JSONObject();
        response.put("return_code","0");
        response.put("return_msg","success");
        return response.toString();
    }
}
