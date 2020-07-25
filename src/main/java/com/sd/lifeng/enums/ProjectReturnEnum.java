package com.sd.lifeng.enums;

import lombok.Getter;

/**
 * @Classname ProjectReturnEnum
 * @Description 项目新增编辑返回码枚举值
 * @author Jason
 * @time 2020-06-26 11:59
 */
@Getter
public enum ProjectReturnEnum {
    SUCCESS("0","success"),
    REPEAT_CHECK_ERROR("J1001","项目名称已存在，请核实"),
    NO_SOURCE_MODEL_ERROR("J1002","系统内尚未配置静态资源"),
    INSERT_SQL_EXCEPTION("J1003","数据操作异常"),
    NO_TIMELINE_MODEL_ERROR("J1004","系统内尚未配置时间线资源"),
    NO_UNIT_PART_MODEL_ERROR("J1006","单位-分部资源未配置"),
    NO_CENT_MODEL_ERROR("J1008","单元字典资源未配置"),
    PROJECT_HAS_NO_PART_ERROR("J1011","查询异常，该项目未添加任何单位-分部"),
    PROJECT_CANNOT_EDIT_ERROR("J1013","该项目不可编辑"),
    PROJECT_DETAIL_UPDATE_ERROR("J1015","项目详情更新失败"),
    PROJECT_SOURCE_UPDATE_ERROR("J1016","项目静态资源更新失败"),
    PROJECT_TIMELINE_UPDATE_ERROR("J1017","项目时间线资源更新失败"),
    PROJECT_CENT_PREDO_ERROR("J1018","单元预处理异常"),
    PROJECT_UNIT_PART_UPDATE_ERROR("J1019","项目单位-分部更新失败"),
    CENT_DELETE_ERROR("J1020","单元移除失败"),
    PROJECT_STATUS_UPDATE_ERROR("J1021","项目状态操作失败");
    private String code;
    private String msg;


    ProjectReturnEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
