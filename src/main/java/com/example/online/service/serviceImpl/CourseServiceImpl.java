package com.example.online.service.serviceImpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;
    @Override
    public int createCourse(Course course) {
        if(course.getCourseName()==null || "".equals(course.getCourseName())){
            log.info("course name can not null");
            return 0;
        }

        return courseMapper.createCourse(course.setCreatedTime(LocalDateTime.now()));
    }

    @Override
    public int updateCourse(Course course) {
        if(course.getCourseId()==null){
            log.info("courseId can not null");
            return 0;
        }
        return courseMapper.updateCourse(course);
    }

    @Override
    public List<Course> queryCourse(Course course) {
        return courseMapper.queryCourses(course);
    }

    @Override
    public int insertLearnUnit(TeachingUnit teachingUnit) {
        if(teachingUnit.getCourseId()==null){
            log.info("courseId can not null");
            return 0;
        }
        if(courseMapper.selectByCourseId(Math.toIntExact(teachingUnit.getCourseId()))==null){
            log.info("course can not be found by courseId");
            return 0;
        }
        if(teachingUnit.getUnitName()==null||"".equals(teachingUnit.getUnitName()))
        {
            log.info("unit_title can not null");
            return 0;
        }
        return courseMapper.insertTeachingUnit(teachingUnit);
    }
    /*多条件跟新学习单元*/
    @Override
    public int updateLearnUnit(TeachingUnit teachingUnit) {
        if(teachingUnit.getId()==null){
            log.info("unitId can not null");
            return 0;
        }
        if(courseMapper.getTeachingUnitBy(teachingUnit.getId())==null){
            log.info("unit can not be found by unitId");
            return 0;
        }
        log.info("unit{}",teachingUnit);
        return courseMapper.updateTeachingUnit(teachingUnit);
    }

    @Override
    public List<TeachingUnit> queryUnit(Integer courseId) {
        if(courseId==null){
            log.info("courseId can not null");
            return null;
        }
        if(courseMapper.selectByCourseId(courseId)==null){
            log.info("course can not be found by courseId");
            return null;
        }
        return courseMapper.queryTeacheingUnitByCourseId(courseId);
    }

    @Override
    public List<Course> queryCourseByStuId(Integer studentId) {
        if(studentId==null){
            log.info("studentId can not null");
            return null;
        }
        if(studentMapper.selectByStudentId(studentId)==null){
            log.info("Student can not be found by courseId");
            return null;
        }
        return courseMapper.queryCourseByStuId(studentId);
    }

}
