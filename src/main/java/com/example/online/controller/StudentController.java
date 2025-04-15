package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.Student;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("/insert")
    @RequiresPermission("userBind")//新增学员
    @CacheEvict(value = "campus:students:query", allEntries = true)
    public ResponseResult<String> insertStudent(@RequestBody Student student){
        int ret=studentService.addStudent(student.setIsFirst(1));
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"新增学员失败");
        else return ResponseResult.success("新增学员成功");
    }
    @PostMapping("/update")
    @RequiresPermission("student")
    @CacheEvict(value = "campus:students:query", allEntries = true)
    public ResponseResult<String> updateStudent(@RequestBody Student student){
        int ret=studentService.updateStudent(student);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"编辑学员失败");
        else return ResponseResult.success("编辑学员成功");
    }
    @PostMapping("/updateStatus")
    @RequiresPermission("student")
    @CacheEvict(value = "campus:students:query", allEntries = true)
    public ResponseResult<String> updateStatus(@RequestBody Student student){
        int ret=studentService.updateStudent(student);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"审核学员失败");
        else return ResponseResult.success("审核学员成功");
    }
    /*废弃了*/
  /*  @PostMapping("/bindClass")
    @RequiresPermission("student")
      // 插入数据时清空缓存
    public ResponseResult<String> bindClass(@RequestBody Student student){
        int ret=studentService.bindClass(student);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"绑定班级失败");
        else return ResponseResult.success("绑定班级成功");
    }*/
    @PostMapping("/bindUser")
    @RequiresPermission("userBind")
    public ResponseResult<String> bindUser(@RequestBody Student student){
        int ret=studentService.bindUser(student);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"绑定用户失败");
        else return ResponseResult.success("绑定用户成功");
    }
    @PostMapping("/query")
    @RequiresPermission("source")
    @Cacheable(cacheNames = "campus:students:query", key = "'studentlist'")
    public ResponseResult<Object> query(@RequestBody Student student){
       return ResponseResult.success(studentService.query(student));
    }
}
