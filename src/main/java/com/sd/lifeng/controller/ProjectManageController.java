package com.sd.lifeng.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lifeng/projectctl")
@CrossOrigin
public class ProjectManageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @PostMapping("/addnewpro")
    public String register(@RequestBody JSONObject req){
        logger.info("register send into msg :"+req);

        JSONObject response = new JSONObject();
        response.put("return_code","0");
        response.put("return_msg","success");
        return response.toString();
    }
}
