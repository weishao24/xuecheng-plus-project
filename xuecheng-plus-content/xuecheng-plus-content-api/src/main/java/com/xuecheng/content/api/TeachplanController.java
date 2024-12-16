package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: weichongzhan
 * @create: 2024-12-09 09:44
 * @description: 课程计划编辑接口
 */
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {

    @Autowired
    TeachplanService teachplanService;



    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程 Id",required =
            true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        List<TeachplanDto> teachplanDtoList = teachplanService.findTeachplanTree(courseId);
        return teachplanDtoList;
    }


    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){

        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划创建或修改")
    @DeleteMapping("/teachplan/{teachplanId}")
    public void deleteTeachplan( @PathVariable Long teachplanId){

        teachplanService.deleteTeachplan(teachplanId);

    }

    @ApiOperation("课程计划向下移动")
    @PostMapping("/teachplan/movedown/{teachplanId}")
    public void movedownTeachplan( @PathVariable Long teachplanId){

        teachplanService.movedownTeachplan(teachplanId);

    }

    @ApiOperation("课程计划向上移动")
    @PostMapping("/teachplan/moveup/{teachplanId}")
    public void moveupTeachplan( @PathVariable Long teachplanId){

        teachplanService.movedupTeachplan(teachplanId);

    }





}
