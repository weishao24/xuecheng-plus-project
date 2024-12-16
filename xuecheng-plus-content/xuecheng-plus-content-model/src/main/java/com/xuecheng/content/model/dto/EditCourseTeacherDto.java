package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: weichongzhan
 * @create: 2024-12-16 11:50
 * @description: 修改师资信息Dto
 */
@Data
@ApiModel(value="EditCourseTeacherDto", description="修改师资基本信息")
public class EditCourseTeacherDto extends AddCourseTeacherDto{

    @ApiModelProperty(value = "教师 id", required = true)
    private Long id;

}
