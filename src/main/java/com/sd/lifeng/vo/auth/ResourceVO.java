package com.sd.lifeng.vo.auth;

import lombok.Data;

/**
 * @author bmr
 * @Classname ResourceVO
 * @Description
 * @Date 2020/5/25 8:47:51
 */
@Data
public class ResourceVO {
    private Integer id;
    private String resourceName;
    private String resourceUrl;
    private Integer parentId;
    private Integer resourceType;
    private String icon;
    private int resourceOrder;
}
