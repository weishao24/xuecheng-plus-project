package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-12-09 16:39
 * @description: 课程基本信息管理业务接口
 */
public interface TeachplanService {
    

    /** 
    * @Author: weichongzhan 
    * @Date: 2024/12/9 11:12 
    * @Description:  查询课程计划树型结构
    * @param courseId 
    * @return: java.util.List<com.xuecheng.content.model.dto.TeachplanDto> 
    */
    public List<TeachplanDto> findTeachplanTree(long courseId);

    /** 
    * @Author: weichongzhan 
    * @Date: 2024/12/9 15:52
    * @Description: 添加或修改课程计划 
    * @param teachplanDto 
    * @return: void 
    */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
    * @Author: weichongzhan
    * @Date: 2024/12/9 17:27
    * @Description:  删除课程计划
    * @param teachplanId
    * @return: void
    */
    public void deleteTeachplan(Long teachplanId);

    /**
    * @Author: weichongzhan
    * @Date: 2024/12/13 17:48
    * @Description:  课程计划下移
    * @param teachplanId
    * @return: void
    */
    public void movedownTeachplan(Long teachplanId);

    /**
     * @Author: weichongzhan
     * @Date: 2024/12/13 17:48
     * @Description:  课程计划上移
     * @param teachplanId
     * @return: void
     */
    public void movedupTeachplan(Long teachplanId);

}
