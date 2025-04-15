package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.*;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */

@Slf4j
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;
    @PostMapping("/create")
    @RequiresPermission("classManager")
    @CacheEvict(value = "campus:class:query", allEntries = true)  // 插入数据时清空缓存
    public ResponseResult<String> createClass(@RequestBody Map<String,String> map){
      String className=map.get("className");
      String activityId=map.get("activityId");
      String classDesc=map.get("classDescription");
      Integer courseId= Integer.valueOf(map.get("courseId"));
      Integer teacherId=Integer.valueOf(map.get("teacherId"));
      /*课程创建时绑定教师id */
      /*教学班的创建必须绑定对应课程 , 课程和教学班是一对多*/
       Course course=new Course().setCourseId(courseId);
      int ret=classService.createClass(new Classes().setClassName(className).setClassDescription(classDesc).setCourse(course), Integer.valueOf(activityId),teacherId);
     if(ret==1){
         return ResponseResult.success("创建成功");
     }else{
         return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"创建课程失败");
     }
    }
    @PostMapping("/update")
    @RequiresPermission("classManager")
    @CacheEvict(value = "campus:class:query", allEntries = true)
    public ResponseResult<String> updateClass(@RequestBody Map<String,String> map){//修改为map  只能修改描述和班级名
        String classId=map.get("classId");
        String className=map.get("className");
        String classDesc=map.get("classDescription");
        Classes classes=new Classes().setClassId(Integer.valueOf(classId)).setClassName(className).setClassDescription(classDesc);
        int ret=classService.update(classes);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"修改失败,class不存在");
        else return ResponseResult.success("修改成功");
    }
    @PostMapping("/query")
    @RequiresPermission("classManager")
    @Cacheable(cacheNames = "campus:class:query", key = "'classlist'")
    public ResponseResult<Object> query(@RequestBody Classes classes){
        List<Classes> classesList=classService.query(classes);
        return ResponseResult.success(classesList);
    };
    /*
    * 查询班级绑定的活动
    * */
    @PostMapping("/queryActivity")
    @RequiresPermission("classManager")
    @Cacheable(cacheNames = "campus:class:queryActivity", key = "#map['classId']")
    public ResponseResult<Object> queryActivity(@RequestBody Map<String,String> map ){
        String classId=map.get("classId");
        List<Activity> activityList=classService.queryActivity(Integer.valueOf(classId));
        return ResponseResult.success(activityList);
    };

    /*
    * 查询班级绑定的老师
    * */
    @PostMapping("/queryTeacher")
    @RequiresPermission("classManager")
    @Cacheable(cacheNames = "campus:class:queryTeacher", key = "#map['classId']")
    public ResponseResult<Object> queryTeacher(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        List<Teacher> teacherList =classService.queryTeacher(Integer.valueOf(classId));
        return ResponseResult.success(teacherList);
    };
    @PostMapping("/bindTeacherByClassName")
    @RequiresPermission("bindTeacherToClass")
    public ResponseResult<String> bindTeacherByClassName(@RequestBody Map<String,String> map){
         String className=map.get("className");
         String teacherId=map.get("teacherId");
         int ret=classService.bindTeacherByClassName(className, Integer.valueOf(teacherId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"绑定失败,class不存在 或者老师不存在");
        else return ResponseResult.success("绑定成功");
    }
    @PostMapping("/bindTeacherByClassId")
    @RequiresPermission("bindTeacherToClass")
    @CacheEvict(cacheNames = "campus:class:queryTeacher", key = "#map['classId']",allEntries = true)
    public ResponseResult<String> bindTeacherByClassId(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String teacherId=map.get("teacherId");
        int ret=classService.bindTeacherByClassId(Integer.valueOf(classId), Integer.valueOf(teacherId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"绑定失败,class不存在 或者老师不存在");
        else return ResponseResult.success("绑定成功");
    }
    @PostMapping("/queryStudentByClassId")
    @RequiresPermission("classManager")
    @Cacheable(cacheNames = "campus:class:queryStudentByClassId", key = "#map['classId']")
    public ResponseResult<Object> queryStudent(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        List<Student> studentList=classService.queryClassStudent(Integer.valueOf(classId));
        return ResponseResult.success(studentList);
    };
    @PostMapping("/addActivityToClass")
    @RequiresPermission("classManager")
    @CacheEvict(cacheNames = "campus:class:queryActivity", key = "#map['classId']",allEntries = true)
    public ResponseResult<String> addActivityToClass(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String activityId=map.get("activityId");
        int ret=classService.addActivityToClass(Integer.valueOf(classId),Integer.valueOf(activityId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"添加活动失败,class不存在 或者活动不存在");
        else return ResponseResult.success("绑定成功");
    };
    @PostMapping("/addStudentToClass")
    @RequiresPermission("classManager")
    @CacheEvict(cacheNames = "campus:class:queryStudentByClassId", key = "#map['classId']",allEntries = true)
    public ResponseResult<String> addStudentToClass(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String studentId=map.get("studentId");
        int ret=classService.addStudentToClass(Integer.valueOf(classId),Integer.valueOf(studentId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"添加学员失败,class不存在 或者学员不存在");
        else return ResponseResult.success("绑定成功");
    };
    @PostMapping("/removeStudentFromClass")
    @RequiresPermission("classManager")
    @CacheEvict(cacheNames = "campus:class:queryStudentByClassId", key = "#map['classId']",allEntries = true)
    public ResponseResult<String>removeStudentFromClass(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String studentId=map.get("studentId");
        int ret=classService.removeStudentFromClass(Integer.valueOf(classId),Integer.valueOf(studentId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"移除学员失败,classId不存在 或者学员不存在");
        else return ResponseResult.success("移除成功");
    }
    @PostMapping("/removeTeacherFromClass")
    @RequiresPermission("classManager")
    @CacheEvict(cacheNames = "campus:class:queryTeacher", key = "#map['classId']",allEntries = true)
    public ResponseResult<String>removeTeacherFromClass(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String teacherId=map.get("teacherId");
        int ret=classService.removeTeacherFromClass(Integer.valueOf(classId),Integer.valueOf(teacherId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"移除教师失败,classId不存在 或者教师不存在");
        else return ResponseResult.success("移除成功");
    }
    @PostMapping("/removeActivityFromClass")
    @RequiresPermission("classManager")
    @CacheEvict(cacheNames = "campus:class:queryActivity", key = "#map['classId']",allEntries = true)
    public ResponseResult<String>removeActivityFromClass(@RequestBody Map<String,String> map){
        String classId=map.get("classId");
        String activityId=map.get("activityId");
        int ret=classService.removeActivityFromClass(Integer.valueOf(classId),Integer.valueOf(activityId));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"移除活动失败,classId不存在 或者活动不存在");
        else return ResponseResult.success("移除成功");
    }
}
