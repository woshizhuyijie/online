package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.Course;
import com.example.campus.pojo.TeachingUnit;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;
    @PostMapping("/create")
    @RequiresPermission("course")
    public ResponseResult<String> createCourse(@RequestBody Course course){
        int ret=courseService.createCourse(course);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"新增课程失败");
        else return ResponseResult.success("新增课程成功");
    }
    @PostMapping("/update")
    @RequiresPermission("course")
    public ResponseResult<String> update(@RequestBody Course course){
        int ret=courseService.updateCourse(course);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"新增课程失败");
        else return ResponseResult.success("课程更新成功");
    }
    @PostMapping("/query")
    @RequiresPermission("course")
    @Cacheable(cacheNames = "campus:course:query", key = "'courselist'")
    public ResponseResult<Object> query(@RequestBody Course course){
        List<Course> courseList=courseService.queryCourse(course);
        // 后处理：去除没有学习单元的课程
        for (Course courses : courseList) {
            courses.setUnitList(courses.getUnitList().stream()
                    .filter(unit -> unit.getId() != null)  // 过滤掉没有ID的学习单元
                    .collect(Collectors.toList()));
        }
        return  ResponseResult.success(courseList);
    }
    @PostMapping("/insertTeachingUnit")
    @RequiresPermission("course")
    @CacheEvict(cacheNames = "campus:course:queryUnit", key = "#teachingUnit.courseId"  )
    public ResponseResult<String> insertTeachingUnit(@RequestBody TeachingUnit teachingUnit){
        log.info(teachingUnit.toString());
        int ret=courseService.insertLearnUnit(teachingUnit);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"新增课程学习单元失败");
        else return ResponseResult.success("新增课程学习单元成功");
    }
    @PostMapping("/updateTeachingUnit")
    @RequiresPermission("course")
    @CacheEvict(cacheNames = "campus:course:queryUnit", key = "#teachingUnit.courseId"  )
    public ResponseResult<String> updateTeachingUnit(@RequestBody TeachingUnit teachingUnit){
        log.info(teachingUnit.toString());
        int ret=courseService.updateLearnUnit(teachingUnit);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"更新课程学习单元失败");
        else return ResponseResult.success("更新课程学习单元成功");
    }
    @PostMapping("/queryUnit")
    @RequiresPermission("user")
    @Cacheable(cacheNames = "campus:course:queryUnit", key = "#map['courseId']")
    public ResponseResult<Object> queryTeachingUnit(@RequestBody Map<String,String> map){
        String courseId=map.get("courseId");
        List<TeachingUnit> unitList=courseService.queryUnit(Integer.valueOf(courseId));
        return ResponseResult.success(unitList);
    }
    @PostMapping("/queryByStudentId")
    @RequiresPermission("queryCourseByStudent")  //通过userId role 来查询这个学生选的课程
    @Cacheable(cacheNames = "campus:course:queryByStudentId", key = "#map['userId']")
    public ResponseResult<Object> queryByStudentId(@RequestBody Map<String,String> map){
        String userId= map.get("userId");
        String role=map.get("role");
        log.info("userId:{} , role: {}",userId,role);
        if(!role.equals("student")){
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"your have not right role to request the api");
        }
        Integer studentId=studentService.getStuIdByUseId(Integer.valueOf(userId));
        List<Course> courseList=courseService.queryCourseByStuId(studentId);
        return ResponseResult.success(courseList) ;
    }
}
