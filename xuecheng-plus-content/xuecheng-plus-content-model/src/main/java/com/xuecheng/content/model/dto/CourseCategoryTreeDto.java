package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-12-03 11:53
 * @description: 课程分类树型结点 dto
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements java.io.Serializable{

    List<CourseCategoryTreeDto> childrenTreeNodes;
}
