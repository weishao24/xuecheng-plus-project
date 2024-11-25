package com.xuecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-11-25 17:32
 * @description: TODO
 */
@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {

        //测试查询接口
        LambdaQueryWrapper<CourseBase> queryWrapper = new
                LambdaQueryWrapper<>();
        //查询条件
        //拼接查询条件
        //根据课程名称模糊查询 name like '%名称%'
        queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
        //根据课程审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,
                courseParamsDto.getAuditStatus());
        //todo:按课程发布状态查询
        //分页参数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),
                pageParams.getPageSize());
        //分页查询 E page 分页参数, @Param("ew") Wrapper<T>queryWrapper 查询条件
        Page<CourseBase> pageResult =
                courseBaseMapper.selectPage(page, queryWrapper);
        //数据
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();
        //准备返回数据 List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new
                PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());

        return courseBasePageResult;
    }
}
