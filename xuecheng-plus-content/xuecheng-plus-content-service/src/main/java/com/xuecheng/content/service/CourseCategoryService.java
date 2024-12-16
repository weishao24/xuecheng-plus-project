package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
* @Author: weichongzhan
* @Date: 2024/12/3 14:51
* @Description: 课程分类业务接口
*/
public interface CourseCategoryService {


    /** 
    * @Author: weichongzhan 
    * @Date: 2024/12/3 14:56 
    * @Description:  课程分类树形结构查询
    * @param id 
    * @return: java.util.List<com.xuecheng.content.model.dto.CourseCategoryTreeDto> 
    */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
