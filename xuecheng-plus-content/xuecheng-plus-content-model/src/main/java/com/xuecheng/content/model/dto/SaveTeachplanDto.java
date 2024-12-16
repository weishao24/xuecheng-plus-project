package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author: weichongzhan
 * @create: 2024-12-09 15:44
 * @description: 保存课程计划dto,包括新增,修改
 */
@Data
@ToString
public class SaveTeachplanDto {

    /***
     * 教学计划 id
     */
    private Long id;
    /**
     * 课程计划名称
     */
    private String pname;
    /**
     * 课程计划父级 Id
     */
    private Long parentid;
    /**
     * 层级，分为 1、2、3 级
     */
    private Integer grade;
    /**
     * 课程类型:1 视频、2 文档
     */
    private String mediaType;
    /**
     * 课程标识
     */
    private Long courseId;
    /**
     * 课程发布标识
     */
    private Long coursePubId;
    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;

}
