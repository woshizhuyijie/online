package com.example.online.mapper;

import com.example.campus.pojo.Course;
import com.example.campus.pojo.TeachingUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Mapper
public interface CourseMapper {
    int createCourse(Course course);
    List<Course> queryCourses(Course course);
    int updateCourse(Course course);
    List<Course> queryByTeacherId(@Param("teacherId") Integer teacherId);
    Course selectByCourseId(@Param("courseId") Integer courseId);
    /*为课程绑定教学班*/
    int BindClassForCourse(@Param("courseId") Integer courseId,@Param("classId") Integer classId);
    /*为课程加入教学单元*/
    int insertTeachingUnit(TeachingUnit teachingUnit);
    /*多条件更新学习单元*/
    int updateTeachingUnit(TeachingUnit teachingUnit);
    TeachingUnit getTeachingUnitBy(@Param("unitId") Long unitId);
    List<TeachingUnit> queryTeacheingUnitByCourseId(@Param("classId") Integer classId);
    //查询出student选的课
    List<Course> queryCourseByStuId(@Param("studentId") Integer studentId);
/*有三个表t_manger_student 有三个键 一个自增的主键 还有就是class_id student_id,另一个表就是t_course_class 一个自增的主键还有就是class_id class_id,另一个表就t_course 主键是course_id,我希望通过student_id，查询出他所在教学班教授的课程列表，如果它不在任何教学班，这个列表就是空，教学班必定绑定一个课程*/
}
