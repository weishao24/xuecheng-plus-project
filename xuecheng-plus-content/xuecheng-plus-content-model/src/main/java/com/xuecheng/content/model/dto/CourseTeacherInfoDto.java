package com.xuecheng.content.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xuecheng.content.model.po.CourseTeacher;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: weichongzhan
 * @create: 2024-12-16 11:14
 * @description: 师资基本信息Dto
 */

@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class CourseTeacherInfoDto extends CourseTeacher {


    /**
     * 教师标识
     */
    private String teacherName;

    /**
     * 教师职位
     */
    private String position;

    /**
     * 教师简介
     */
    private String introduction;

    /**
     * 照片
     */
    private String photograph;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

}
