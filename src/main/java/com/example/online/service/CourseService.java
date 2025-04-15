package com.example.campus.service;

import com.example.campus.pojo.Activity;
import com.example.campus.pojo.Course;
import com.example.campus.pojo.TeachingUnit;

import java.util.List;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface CourseService {
    int createCourse(Course course);
    int updateCourse(Course course);
    List<Course> queryCourse(Course course);
    /*创建学习单元*/
    int insertLearnUnit(TeachingUnit teachingUnit);
    /*更新学习单元*/
    int updateLearnUnit(TeachingUnit teachingUnit);
    /*查询学习单元通过courseID*/
    List<TeachingUnit> queryUnit(Integer courseId);
    List<Course>  queryCourseByStuId(Integer studentId);//通过studentId 查询学生的选课
}
