package com.xuecheng.content;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: weichongzhan
 * @create: 2024-11-25 16:39
 * @description: TODO
 */
@SpringBootTest
public class CourseBaseInfoServiceTests {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testCourseBaseInfoService(){

        //查询条件
        QueryCourseParamsDto queryCourseParamsDto = new
                QueryCourseParamsDto();
        queryCourseParamsDto.setCourseName("java");
        queryCourseParamsDto.setAuditStatus("202004");//课程审核通过

        //分页参数
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(2L);//页码
        pageParams.setPageSize(2L);//每页记录数

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);
        System.out.println(courseBasePageResult);




    }
}
