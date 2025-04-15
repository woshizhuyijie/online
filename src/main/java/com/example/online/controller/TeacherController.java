package com.example.campus.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.Student;
import com.example.campus.pojo.Teacher;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
* * @author woshizhuyijie
 * @date 2024-12-22
 *
* */
@RestController
@RequestMapping("/teacher")
public class TeacherController {//教师管理
    @Autowired
    TeacherService teacherService;
    @PostMapping("/insert")
    @RequiresPermission("userBind")//新增教师
    @CacheEvict(value = "campus:teacher:query", allEntries = true)  // 插入数据时清空缓存
    public ResponseResult<String> insertTeacher(@RequestBody Teacher teacher){
        int ret=teacherService.insertTeacher(teacher);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"新增教师失败");
        else return ResponseResult.success("新增教师成功");
    }
    @PostMapping("/query")
    @RequiresPermission("source")
    @Cacheable(cacheNames = "campus:teacher:query", key = "'teacherlist'")
    public ResponseResult<Object> query(@RequestBody Teacher teacher){
        return ResponseResult.success(teacherService.query(teacher));
    }
    @PostMapping("/update")
    @RequiresPermission("userBind")//新增教师
    @CacheEvict(value = "campus:teacher:query", allEntries = true)  // 插入数据时清空缓存
    public ResponseResult<String> updateTeacher(@RequestBody Teacher teacher){
        int ret=teacherService.update(teacher);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"更新教师失败");
        else return ResponseResult.success("更新教师成功");
    }
}
