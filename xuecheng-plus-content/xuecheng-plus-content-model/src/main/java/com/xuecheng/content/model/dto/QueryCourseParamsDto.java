package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author weichongzhan
 * @version 1.0
 * @description 课程查询条件模型类
 * @date 2024/11/25 15:37
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;

}
