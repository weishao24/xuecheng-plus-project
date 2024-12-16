package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: weichongzhan
 * @create: 2024-12-16 10:27
 * @description: 师资管理接口
 */

@Api(value="师资管理接口",tags="师资管理接口")
@RestController
public class CourseTeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;

    @ApiOperation("师资查询接口")
    @GetMapping("/courseTeacher/list/{courseId}")
    public PageResult<CourseTeacher> list(PageParams pageParams,@PathVariable @Validated Long courseId){

        PageResult<CourseTeacher> pageResult = courseTeacherService.queryCourseTeacherList(pageParams,courseId);
        return pageResult;

    }

    @ApiOperation("新增师资信息")
    @PostMapping("/courseTeacher")
    public CourseTeacherInfoDto createCourseTeacher(@RequestBody @Validated({ValidationGroups.Insert.class}) AddCourseTeacherDto addCourseTeacherDto){

        return courseTeacherService.createCourseTeacher(addCourseTeacherDto);

    }

    @ApiOperation("修改师资基础信息")
    @PutMapping("/courseTeacher")
    public CourseTeacherInfoDto modifyCourseTeacher(@RequestBody @Validated EditCourseTeacherDto editCourseTeacherDto){

        return courseTeacherService.updateCourseTeacher(editCourseTeacherDto);
    }

    @ApiOperation("删除教师信息")
    @DeleteMapping("/ourseTeacher/course/{courseId}/{teacherId}")
    public void deleteTeacher( @PathVariable Long courseId,@PathVariable Long teacherId){

        courseTeacherService.deleteTeacher(courseId,teacherId);

    }



}
