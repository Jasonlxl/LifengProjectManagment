package com.sd.lifeng.vo.user;

import com.sd.lifeng.vo.auth.ResourceVO;
import com.sd.lifeng.vo.auth.RoleVO;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname LoginResponseVO
 * @Description
 * @Author bmr
 * @Date 2020/5/24 9:28:51
 */
@Data
public class LoginResponseVO {

    private String token;

    private int userId;

    private String userName;

    private String realName;

    private int userType;

    private String userTypeName;

    private String createTime;

    private Set<RoleVO> roleVOList = new HashSet<>();

    private Set<ResourceVO> resourceVOList = new HashSet<>();
}
