package com.xuecheng.content.model.dto;

import com.xuecheng.base.exception.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: weichongzhan
 * @create: 2024-12-16 11:06
 * @description: 添加师资Dto
 */

@Data
@ApiModel(value="AddCourseTeacherDto", description="新增师资基本信息")
public class AddCourseTeacherDto {

    @NotNull(groups = {ValidationGroups.Insert.class},message = "课程ID不能为空")
    @NotNull(groups = {ValidationGroups.Update.class},message = "课程ID不能为空")
    @ApiModelProperty(value = "课程ID", required = true)
    private Long courseId;

    @NotEmpty(groups = {ValidationGroups.Insert.class},message = "添加教师名称不能为空")
    @NotEmpty(groups = {ValidationGroups.Update.class},message = "修改教师名称不能为空")
    @ApiModelProperty(value = "教师名称", required = true)
    private String teacherName;

    @NotEmpty(message = "教师职位不能为空")
    @ApiModelProperty(value = "教师职位", required = true)
    private String position;

    @ApiModelProperty(value = "教师简介")
    private String introduction;

    @ApiModelProperty(value = "照片")
    private String photograph;

}
