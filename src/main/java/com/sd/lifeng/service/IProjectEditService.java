package com.sd.lifeng.service;

import com.alibaba.fastjson.JSONObject;

public interface IProjectEditService {
    /*
   查询单位-分部方法，并标注已存在的单位分部
   */
    public JSONObject queryUnitPart(String projectHash);
}
