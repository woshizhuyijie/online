package com.example.online.mapper;

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
public interface TeacherMapper {
    List<Teacher> queryTeachers(Teacher teacher);
    int updateTeacher(Teacher teacher);
    int deleteTeacherById(@Param("id") Integer id);
    // 多条件插入方法
    int insertTeacher(Teacher teacher);
    Teacher selectByIdTeacher(@Param("id") Integer teacherId);
    int countTeacher();
}
