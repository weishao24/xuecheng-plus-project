package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;

/**
* @Author: weichongzhan
* @Date: 2024/12/16 10:36
* @Description:师资管理业务接口
*/
public interface CourseTeacherService {

    public PageResult<CourseTeacher> queryCourseTeacherList(PageParams pageParams, Long courseId);

    public CourseTeacherInfoDto createCourseTeacher(AddCourseTeacherDto addCourseTeacherDto);

    public CourseTeacherInfoDto updateCourseTeacher(EditCourseTeacherDto editCourseTeacherDto);

    public void deleteTeacher(Long courseId,Long teacherId);


}
