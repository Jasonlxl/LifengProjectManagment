package com.sd.lifeng.domain;

import lombok.Data;

import java.util.Set;

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
    private Integer parentId;
    private String icon;
    private int resourceOrder;
    private String createTime;
    private String updateTime;
    private Set<SystemResourceDO> children;
}
