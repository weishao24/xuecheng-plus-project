package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: weichongzhan
 * @create: 2024-12-05 17:50
 * @description: 修改课程基本信息
 */
@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程 id", required = true)
    private Long id;
}
