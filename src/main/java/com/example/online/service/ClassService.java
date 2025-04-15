package com.example.campus.service;

import com.example.campus.pojo.Activity;
import com.example.campus.pojo.Classes;
import com.example.campus.pojo.Student;
import com.example.campus.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface ClassService {
    int createClass(Classes classes,Integer activityId,Integer teacherId);
    int update(Classes classes);
    //添加教师
    int bindTeacherByClassName(String className,Integer teacherId);
    int bindTeacherByClassId(Integer classId,Integer teacherId);
    //多条件查询
    List<Classes> query(Classes classes);
    //获取班级绑定的活动
    List<Activity> queryActivity(Integer classId);
    //获取班级绑定的教师
    List<Teacher> queryTeacher(Integer classId);
    //查询教学班的学生
    List<Student> queryClassStudent(Integer classId);
    /*添加活动*/
    int addActivityToClass(Integer classId,Integer activityId);
    /*添加学生*/
    int addStudentToClass(Integer classId,Integer studentId);
    /*移除教学班的学生*/
   int removeStudentFromClass(Integer classId, Integer studentId);
   /*移除教学班的教师*/
   int removeTeacherFromClass(Integer classId, Integer teacherId);
   int removeActivityFromClass( Integer classId,  Integer activityId);

}
