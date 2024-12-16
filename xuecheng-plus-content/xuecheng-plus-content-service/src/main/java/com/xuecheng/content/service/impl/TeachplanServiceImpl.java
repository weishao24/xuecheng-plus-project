package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-12-09 11:13
 * @description: 课程计划 service 接口实现类
 */
@Service
public class TeachplanServiceImpl implements TeachplanService{

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //根据上传的课程计划id判断是新增还是修改，如为空则新增，反之即修改
        Long teachplanId = teachplanDto.getId();

        if(teachplanId == null){
            Teachplan teachplanNew = new Teachplan();
            BeanUtils.copyProperties(teachplanDto,teachplanNew);
            int count= getTeachplanCount(teachplanNew.getCourseId(),teachplanNew.getParentid());
            teachplanNew.setOrderby(count+1);
            teachplanMapper.insert(teachplanNew);
        }else{

            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);

        }

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteTeachplan(Long teachplanId) {

        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        //判断删除的是大章节还是小章节
        //是小章节
        if(teachplan.getParentid() != 0){

            //根据mediaType属性判断小章节是否关联视频
            String mediaType = teachplan.getMediaType();
            if (mediaType == null) {
                //证明没有关联视频或者文档
                //删除小章节
                teachplanMapper.deleteById(teachplan);
            } else {
                //证明关联了视频或者文档
                //删除视频，暂不考虑文档的情况
                teachplanMediaMapper.deleteById(teachplanId);
                //删除小章节
                teachplanMapper.deleteById(teachplan);
            }

            // 删除媒资表数据
         //   teachplanMediaMapper.delete(new QueryWrapper<TeachplanMedia>().eq("teachplan_id", teachplanId));
            // 删除课程计划表信息
          //  teachplanMapper.deleteById(teachplanId);

        }else //是大章节
        {
            //判断大章节下面是否有小章节
            int count = teachplanMapper.selectBigCount(teachplanId);
            //
            if(count == 0 ){
               //大章节下没有小章节，可以删除
                teachplanMapper.deleteById(teachplanId);
            }else{
                //大章节下有小章节，不可以删除
                XueChengPlusException.cast("课程计划信息还有子级信息，无法操作",120409);
            }

        }

    }

    @Override
    public void movedownTeachplan(Long teachplanId) {
        //根据课程计划id查询出当前课程计划
        Teachplan teachplan =teachplanMapper.selectById(teachplanId);

        if(teachplan.getParentid() != 0){
            Integer orderBy = teachplan.getOrderby();
            //是小章节
            //根据父章节的id，找出大章节下面所有小节点
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid()).eq(Teachplan::getGrade,2);
            List<Teachplan>  teachplanList = teachplanMapper.selectList(queryWrapper);
            int i = 0;
            for(Teachplan t:teachplanList ){
                if(t.getOrderby() == orderBy+1){//得到下一个计划
                    i++;
                }
                if(i==1){
                    teachplan.setOrderby(t.getOrderby());
                    t.setOrderby(orderBy);
                    teachplanMapper.updateById(t);
                    teachplanMapper.updateById(teachplan);
                    break;
                }
            }
            if(i==0){
                XueChengPlusException.cast("当前计划已经在末尾，无法交换");
            }

        }else{
            //是大章节
            //根据课程的id，找到所有大章节
            Integer orderBy = teachplan.getOrderby();
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getCourseId,teachplan.getCourseId()).eq(Teachplan::getGrade,1);
            List<Teachplan>  teachplanList = teachplanMapper.selectList(queryWrapper);
            int i = 0;
            for(Teachplan t:teachplanList ){
                if(t.getOrderby() == orderBy+1){//得到下一个大章节
                    i++;
                }
                if(i==1){
                    //进行交换
                    teachplan.setOrderby(t.getOrderby());
                    t.setOrderby(orderBy);
                    teachplanMapper.updateById(t);
                    teachplanMapper.updateById(teachplan);
                    break;
                }
            }
            if(i==0){
                XueChengPlusException.cast("当前大章节已经在末尾，无法交换");
            }

        }

    }

    @Override
    public void movedupTeachplan(Long teachplanId) {
//根据课程计划id查询出当前课程计划
        Teachplan teachplan =teachplanMapper.selectById(teachplanId);

        if(teachplan.getParentid() != 0){
            Integer orderBy = teachplan.getOrderby();
            //是小章节
            //根据父章节的id，找出大章节下面所有小节点
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid()).eq(Teachplan::getGrade,2);
            List<Teachplan>  teachplanList = teachplanMapper.selectList(queryWrapper);
            int i = 0;
            for(Teachplan t:teachplanList ){
                if(t.getOrderby() == orderBy-1&&(orderBy-1) != 0){//得到下一个计划
                    i++;
                }
                if(i==1){
                    teachplan.setOrderby(t.getOrderby());
                    t.setOrderby(orderBy);
                    teachplanMapper.updateById(t);
                    teachplanMapper.updateById(teachplan);
                    break;
                }
            }
            if(i==0){
                XueChengPlusException.cast("当前计划已经在末尾，无法交换");
            }

        }else{
            //是大章节
            //根据课程的id，找到所有大章节
            Integer orderBy = teachplan.getOrderby();
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getCourseId,teachplan.getCourseId()).eq(Teachplan::getGrade,1);
            List<Teachplan>  teachplanList = teachplanMapper.selectList(queryWrapper);
            int i = 0;
            for(Teachplan t:teachplanList ){
                if(t.getOrderby() == orderBy-1&&(orderBy-1) != 0){//得到下一个大章节
                    i++;
                }
                if(i==1){
                    //进行交换
                    teachplan.setOrderby(t.getOrderby());
                    t.setOrderby(orderBy);
                    teachplanMapper.updateById(t);
                    teachplanMapper.updateById(teachplan);
                    break;
                }
            }
            if(i==0){
                XueChengPlusException.cast("当前大章节已经在末尾，无法交换");
            }

        }

    }

    /** 
    * @Author: weichongzhan 
    * @Date: 2024/12/9 16:00 
    * @Description:  获取最新的排序号
    * @param courseId 课程 id
    * @param parentId 父课程计划 id
    * @return: int 最新排序号
    */
    private int getTeachplanCount(long courseId,long parentId){

        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);

        Integer count = teachplanMapper.selectCount(queryWrapper);

        return count;

        
    }


}
