package com.sd.lifeng.vo.auth;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ResourceTreeVO {
    private Integer id;
    private String name;
    private String icon;
    private String path;
    private Integer parentId;
    private Integer resourceType;
    private int resourceOrder;
    private Set<ResourceTreeVO> children = new HashSet<>();
}
