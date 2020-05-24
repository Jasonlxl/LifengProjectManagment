package com.sd.lifeng.domain;

import lombok.Data;

/**
 * @author bmr
 * @Classname SystemResourceDO
 * @Description
 * @Date 2020/5/24 10:28:51
 */
@Data
public class SystemResourceDO {
    private Integer id;
    private String resourceName;
    private String resourceUrl;
    private Integer resourceType;
    private String createTime;
    private String updateTime;
}
