package com.example.online.mapper;

import com.example.campus.pojo.Activity;
import com.example.campus.pojo.Classes;
import com.example.campus.pojo.Student;
import com.example.campus.pojo.Teacher;
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
public interface ClassesMapper {
    // 多条件插入班级信息
    int insertClass(Classes classes);

    // 多条件查询班级信息（包含活动列表和教师列表）
    List<Classes> queryClasses(Classes classes);

    // 多条件更新班级信息
    int updateClass(Classes classes);
    // 通过已存在的班级获取班级绑定的活动
    List<Activity> queryActivityList(@Param("classId") Integer classId);
    // 通过已存在的班级获取班级绑定的老师
    List<Teacher> queryTeacherList(@Param("classId") Integer classId);
    // 给教学班级绑定老师
    int bindTeacher(@Param("teacherId") Integer  teacherId ,@Param("classId") Integer classId);
    //给班级绑定活动
    int bindActivity(@Param("activityId") Integer  activityId ,@Param("classId") Integer classId);
     Classes selectByClassName(@Param("className") String className);
     Classes selectByClassId(@Param("id") Integer classId);
     //教学班绑定课程
    //教学班查询学生名单
    //为教学班绑定学生
    int bindStudentForClass(@Param("classId") Integer classId,@Param("studentId") Integer StudentId);
   List<Student> selectStudentByClassId(@Param("classId") Integer classId);
   /*移除教学班学生*/
   int removeStudentFromClass(@Param("classId") Integer classId,@Param("studentId") Integer StudentId);
   /*移除教学班教师*/
    int removeTeacherFromClass(@Param("classId") Integer classId,@Param("teacherId") Integer teacherId);
    /*移除教学班活动*/
    int removeActivityFromClass(@Param("classId") Integer classId,@Param("activityId") Integer activityId);
}
