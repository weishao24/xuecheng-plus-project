package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
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
 * @create: 2024-11-25 17:32
 * @description: 课程信息管理接口实现类
 */
@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;


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

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        //参数合法性校验
//        if (StringUtils.isBlank(dto.getName())) {
//            throw new XueChengPlusException("课程名称为空");
//        }
//        if (StringUtils.isBlank(dto.getMt())) {
//            throw new XueChengPlusException("课程分类为空");
//        }
//        if (StringUtils.isBlank(dto.getSt())) {
//            throw new XueChengPlusException("课程分类为空");
//        }
//        if (StringUtils.isBlank(dto.getGrade())) {
//            throw new XueChengPlusException("课程等级为空");
//        }
//        if (StringUtils.isBlank(dto.getTeachmode())) {
//            throw new XueChengPlusException("教育模式为空");
//        }
//        if (StringUtils.isBlank(dto.getUsers())) {
//            throw new XueChengPlusException("适应人群为空");
//        }
//        if (StringUtils.isBlank(dto.getCharge())) {
//            throw new XueChengPlusException("收费规则为空");
//        }

        //添加课程基本信息
        CourseBase courseBaseNew = new CourseBase();

        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setAuditStatus("202002");//审核状态
        courseBaseNew.setStatus("203001");//发布状态
        courseBaseNew.setCreateDate(LocalDateTime.now());

        BeanUtils.copyProperties(dto,courseBaseNew);
        int insert = courseBaseMapper.insert(courseBaseNew);
        if(insert<=0){
              throw new XueChengPlusException("新增课程基本信息失败");
        }
        //添加课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        BeanUtils.copyProperties(dto,courseMarketNew);
        int i = saveCourseMarket(courseMarketNew);

        if(i<=0){
            throw new XueChengPlusException("保存课程营销信息失败");
        }


       // 查询课程基本信息及营销信息并返回
        return getCourseBaseInfoDto(courseId);
    }

    @Override
    public CourseBaseInfoDto getCourseBaseInfoDto(Long courseId) {
        //根据课程id查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }

        //根据课程id查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        //组装
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //查询课程分类
        CourseCategory courseCategoryBySt =
                courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt =
                courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;

    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {

        //根据课程id查询旧的课程信息
        Long courseId = dto.getId();
        CourseBase courseBase = getCourseBaseInfoDto(courseId);
        if(courseBase == null){
            XueChengPlusException.cast("课程不存在");
        }

        if(!courseBase.getCompanyId().equals(companyId)){

            XueChengPlusException.cast("本机构只能修改本机构的课程");

        }

        //封装基本信息的数据
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        //更新课程基本信息
        int i = courseBaseMapper.updateById(courseBase);
        //封装营销信息的数据
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);
        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfoDto(courseId);
        return courseBaseInfo;
    }

    private int saveCourseMarket(CourseMarket courseMarketNew){
        //参数校验
        String charge = courseMarketNew.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new XueChengPlusException("收费规则没有选择");
        }
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0){
               throw new XueChengPlusException("课程为收费价格不能为空且必须大于 0");
//                XueChengPlusException.cast("课程为收费价格不能为空且必须大于 0");
            }
        }

        //根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarketObj == null ){
            return courseMarketMapper.insert(courseMarketNew);
        }else{

            BeanUtils.copyProperties(courseMarketNew,courseMarketObj);
            courseMarketObj.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarketObj);

        }

    }


}
