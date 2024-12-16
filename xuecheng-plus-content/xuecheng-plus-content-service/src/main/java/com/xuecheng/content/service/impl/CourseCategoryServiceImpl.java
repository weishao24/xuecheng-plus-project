package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: weichongzhan
 * @create: 2024-12-03 14:52
 * @description: 课程分类业务接口实现类
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {


    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {

        //查出记录
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        //将List转化为map,已备使用,过滤根节点
        Map<String, CourseCategoryTreeDto> mapTemp =
                courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId()
                )).collect(Collectors.toMap(key -> key.getId(), value -> value,
                        (key1, key2) -> key2));
        //定义一个返回的List
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        //依次遍历每个元素，排除根节点
        courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId()
        )).forEach(item -> {
            if(item.getParentid().equals(id)){
                categoryTreeDtos.add(item);
            }
            //找到当前节点的父节点
            CourseCategoryTreeDto courseCategoryParent = mapTemp.get(item.getParentid());
            if(courseCategoryParent != null){
                if(courseCategoryParent.getChildrenTreeNodes() == null){
                    courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //到每个节点的子节点放在父节点的childrenTreeNodes属性中
                courseCategoryParent.getChildrenTreeNodes().add(item);
            }

        });

        return categoryTreeDtos;
    }

}
