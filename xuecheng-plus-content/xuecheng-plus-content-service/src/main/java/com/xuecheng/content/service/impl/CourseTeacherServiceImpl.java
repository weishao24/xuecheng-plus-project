package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.AddCourseTeacherDto;
import com.xuecheng.content.model.dto.CourseTeacherInfoDto;
import com.xuecheng.content.model.dto.EditCourseTeacherDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-12-16 10:37
 * @description: TODO
 */
@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    CourseTeacherMapper courseTeacherMapper;


    /**
    * @Author: weichongzhan
    * @Date: 2024/12/16 10:40
    * @Description:  师资信息查询
    * @param pageParams
    * @param courseId
    * @return: com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseTeacher>
    */
    @Override
    public PageResult<CourseTeacher> queryCourseTeacherList(PageParams pageParams, Long courseId) {

        LambdaQueryWrapper<CourseTeacher> queryWrapper = new
                LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotEmpty(String.valueOf(courseId)),CourseTeacher::getCourseId, courseId);

        //分页参数
        Page<CourseTeacher> page = new Page<>(pageParams.getPageNo(),
                pageParams.getPageSize());
        //分页查询 E page 分页参数, @Param("ew") Wrapper<T>queryWrapper 查询条件
        Page<CourseTeacher> pageResult = courseTeacherMapper.selectPage(page,queryWrapper);
        //数据
        List<CourseTeacher> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();
        //准备返回数据 List<T> items, long counts, long page, long pageSize
        PageResult<CourseTeacher> courseTeacherPageResult = new
                PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());

        return courseTeacherPageResult;
    }

    @Transactional
    @Override
    public CourseTeacherInfoDto createCourseTeacher(AddCourseTeacherDto addCourseTeacherDto) {

        CourseTeacher courseTeacher = new CourseTeacher();

        courseTeacher.setCreateDate(LocalDateTime.now());

        BeanUtils.copyProperties(addCourseTeacherDto,courseTeacher);
        int insert = courseTeacherMapper.insert(courseTeacher);
        if(insert<=0){
            throw new XueChengPlusException("新增师资基本信息失败");
        }

        Long courseTeacherId = courseTeacher.getId();
        courseTeacher.setId(courseTeacherId);

        return getCourseTeacherInfoDto(courseTeacherId);
    }

    @Override
    public CourseTeacherInfoDto updateCourseTeacher(EditCourseTeacherDto editCourseTeacherDto) {

        //查询要修改的教师信息
        Long teacherId = editCourseTeacherDto.getId();

        CourseTeacher courseTeacher = courseTeacherMapper.selectById(teacherId);

        if(courseTeacher == null){
            XueChengPlusException.cast("教师不存在");
        }

        courseTeacher.setCreateDate(LocalDateTime.now());

        BeanUtils.copyProperties(editCourseTeacherDto,courseTeacher);

        int i= courseTeacherMapper.updateById(courseTeacher);
        if(i<=0){
            throw new XueChengPlusException("修改师资基本信息失败");
        }

        return getCourseTeacherInfoDto(teacherId);

    }

    @Override
    public void deleteTeacher(Long courseId, Long teacherId) {
        //按条件查询教师信息
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new
                LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotEmpty(String.valueOf(courseId)),CourseTeacher::getCourseId, courseId)
                    .eq(StringUtils.isNotEmpty(String.valueOf(teacherId)),CourseTeacher::getId,teacherId);
        CourseTeacher courseTeacher = courseTeacherMapper.selectOne(queryWrapper);

        if(courseTeacher == null){

            XueChengPlusException.cast("未知错误，请稍后重视");

        }

        //进行删除操作
        courseTeacherMapper.deleteById(courseTeacher);

    }

    private CourseTeacherInfoDto getCourseTeacherInfoDto(Long courseTeacherId){

        CourseTeacherInfoDto courseTeacherInfoDto = new CourseTeacherInfoDto();
        CourseTeacher courseTeacher = courseTeacherMapper.selectById(courseTeacherId);

        BeanUtils.copyProperties(courseTeacher,courseTeacherInfoDto);

        return courseTeacherInfoDto;
    }
}
