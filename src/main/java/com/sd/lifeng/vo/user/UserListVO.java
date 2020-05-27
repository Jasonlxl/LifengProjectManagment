package com.sd.lifeng.vo.user;


import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname UserListVO
 * @Description
 * @Author bmr
 * @Date 2020/5/23 14:26:51
 */
@Data
public class UserListVO {
    private Integer userId;
    private String telNo;
    private String realName;
    private Integer type;
    private String typeName;
    private String roleName;
    private String createDate;
    private Set<RoleVO> roleList = new HashSet<>();
    private Set<ResourceVO> resourceList = new HashSet<>();

}
