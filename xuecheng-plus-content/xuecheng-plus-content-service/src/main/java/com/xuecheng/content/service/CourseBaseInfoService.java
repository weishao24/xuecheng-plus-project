package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
* @Author: weichongzhan
* @Date: 2024/11/25 17:27
* @Description:课程信息管理接口
*/

public interface CourseBaseInfoService {

    /**
    * @Author: weichongzhan
    * @Date: 2024/11/25 17:37
    * @Description:  课程分页查询
    * @param pageParams 分页查询参数
    * @param courseParamsDto 查询条件
    * @return: 查询结果
    */
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
    * @Author: weichongzhan
    * @Date: 2024/12/4 16:38
    * @Description:  添加课程信息
    * @param companyId 机构id
    *  @param addCourseDto  课程基本信息
    * @return: com.xuecheng.content.model.dto.CourseBaseInfoDto
    */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
    * @Author: weichongzhan
    * @Date: 2024/12/5 17:53
    * @Description: 根据课程id查询课程信息
    * @param courseId
    * @return: com.xuecheng.content.model.dto.CourseBaseInfoDto
    */
    public CourseBaseInfoDto getCourseBaseInfoDto(Long courseId);

    /**
    * @Author: weichongzhan
    * @Date: 2024/12/5 18:03
    * @Description:  修改课程信息
    * @param companyId
    * @param dto
    * @return: com.xuecheng.content.model.dto.CourseBaseInfoDto
    */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);



}
