package com.example.online.mapper;

import com.example.campus.pojo.Student;
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
public interface StudentMapper {
    int insertStudent(Student student);          // 插入学生记录
    int deleteStudentById( @Param("id") Integer id);           // 按主键删除学生
    List<Student> queryStudents(Student student); // 多条件查询学生
    // 可以查询班级的学生
    //多条件更新学生
    int updateStudent(Student student);
    int countNumber();//统计有多少个学生
    List<Student> selectByStudentNameStudent(@Param("studentName") String studentName);
    Student selectByStudentId(@Param("studentId") Integer studentId);
    Student selectByLoginId(@Param("loginId") Integer loginId);
    int getStuIdByLoginId(@Param("loginId") Integer loginId);
}
