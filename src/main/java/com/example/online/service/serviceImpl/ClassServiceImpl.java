package com.example.online.service.serviceImpl;


import com.example.campus.service.ClassService;
import com.example.online.mapper.*;
import com.example.online.pojo.Activity;
import com.example.online.pojo.Course;
import com.example.online.pojo.Student;
import com.example.online.pojo.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.campus.utils.EmptyUtils.throwSQlE;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */

@Service
@Slf4j
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassesMapper classesMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;
    @Transactional(rollbackFor = SQLException.class)//发生sql错误就回滚
    @Override/*创建的教学班一定要绑定课程并，在教学班和课程关联表中记录 再管理教师id*/
    public int createClass(Classes classes, Integer activityId,Integer teacherId)  {
        if(classes.getClassName()==null || "".equals(classes.getClassName())){
            log.info("className can not null");
            return 0;
        }
        if(activityId==null){
            log.info("activityId can not null");
            return 0;
        }
        if(teacherId==null){
            log.info("teacherId can not");
        }
        Classes classes1=classesMapper.selectByClassName(classes.getClassName());
        if(classes1!=null){
            log.info("class already exist");
            return 0;
        }
        Activity activity=activityMapper.selectById(activityId);
        if(activity==null){
            log.info("activity is not exist");
            return 0;
        }
        Teacher teacher=teacherMapper.selectByIdTeacher(teacherId);
        if(teacher==null){
            log.info("teacher is not exist");
            return 0;
        }
        /*教学班的创建必须绑定对应的课程*/
        Course course=classes.getCourse();
        Course courseSelected=courseMapper.selectByCourseId(course.getCourseId());
        if(courseSelected==null){
            log.info("course is not exist");
            return 0;
        }
        try{
            /*插入成功之后会把生成classId返回给classe*/
            int r1=classesMapper.insertClass(classes.setCreatedTime(LocalDateTime.now()));
            if(r1==0)  throwSQlE("ClassServiceImpl classesMapper.insertClass fail");
            //获取已经插入的id
            //Classes classes2=classesMapper.selectByClassName(classes.getClassName());
            /*绑定活动*/
            int r2=classesMapper.bindActivity(activityId,classes.getClassId());
            if(r2==0)  throwSQlE("ClassServiceImpl classesMapper.bindActivity fail");
            /*给课程和class进行绑定写入到course_class里面*/
            int r3=courseMapper.BindClassForCourse(courseSelected.getCourseId(),classes.getClassId());
            if(r3==0)  throwSQlE("ClassServiceImpl courseMapper.BindClassForCourse fail");
            int r4=classesMapper.bindTeacher(teacherId,classes.getClassId());
            if(r4==0)  throwSQlE("ClassServiceImpl classesMapper.bindTeacher fail");
        }catch(SQLException e){
            e.printStackTrace();
            log.info("insert class fail");
            return 0;
        }

        return 1;
    }

    @Override
    public int update(Classes classes) {
        if(classes.getClassId()==null){
            log.info("classId can not null");
            return 0;
        }

        return classesMapper.updateClass(classes);
    }

    @Override
    public int bindTeacherByClassName(String className, Integer teacherId) {
        if(teacherId==null){
            log.info("teacherId can not null");
        }
        Teacher teacher=teacherMapper.selectByIdTeacher(teacherId);
        if(teacher==null){
            log.info("teacher not exist");
            return 0;
        }
         if(className==null || "".equals(className)){
             log.info("className can not null");
             return 0;
         }
         Classes classes=classesMapper.selectByClassName(className);
         if(classes==null){
             log.info("class not exist");
             return 0;
         }

        return classesMapper.bindTeacher(teacherId,classes.getClassId());

    }

    @Override
    public int bindTeacherByClassId(Integer classId, Integer teacherId) {
        if(teacherId==null){
            log.info("teacherId can not null");
        }
        Teacher teacher=teacherMapper.selectByIdTeacher(teacherId);
        if(teacher==null){
            log.info("teacher not exist");
            return 0;
        }
        if(classId==null){
            log.info("classId can not null");
            return 0;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return 0;
        }
        return classesMapper.bindTeacher(teacherId,classId);
    }


    @Override
    public List<Classes> query(Classes classes) {
        return classesMapper.queryClasses(classes);
    }

    @Override
    public List<Activity> queryActivity(Integer classId) {
        if(classId==null){
            log.info("classId can not null");
            return null;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return null;
        }
        return classesMapper.queryActivityList(classId);
    }

    @Override
    public List<Teacher> queryTeacher(Integer classId) {
        if(classId==null){
            log.info("classId can not null");
            return null;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return null;
        }
        return classesMapper.queryTeacherList(classId);
    }
    /*查询教学班的学生*/
    @Override
    public List<Student> queryClassStudent(Integer classId) {
        if(classId==null){
            log.info("classId can not null");
            return null;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return null;
        }
        return classesMapper.selectStudentByClassId(classId);
    }

    @Override
    public int addActivityToClass(Integer classId, Integer activityId) {
        if(classId==null){
            log.info("classId can not null");
            return 0;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return 0;
        }
        if(activityId==null){
            log.info("activityId can not null");
            return 0;
        }
        Activity activity=activityMapper.selectById(activityId);
        if(activity==null){
            log.info("activity  not exist");
            return 0;
        }
        return classesMapper.bindActivity(activityId,classId);
    }

    /*为教学班添加学员*/
    @Override
    public int addStudentToClass(Integer classId, Integer studentId) {
        if(classId==null){
            log.info("classId can not null");
            return 0;
        }
        Classes classes=classesMapper.selectByClassId(classId);
        if(classes==null){
            log.info("class not exist");
            return 0;
        }
        if(studentId==null){
            log.info("studentId can not null");
            return 0;
        }
        Student student=studentMapper.selectByStudentId(studentId);
        if(student==null){
            log.info("student  not exist");
            return 0;
        }

        return classesMapper.bindStudentForClass(classId,studentId);
    }

    @Override
    public int removeStudentFromClass(Integer classId, Integer studentId) {
        /*前端移除的学员 必定是这个班级的学生所以后端就不额外进行判断了*/
        if(classId==null){
            log.info("classId can not null");
        }
        if(studentId==null){
            log.info("studentId can not null");
        }
        return classesMapper.removeStudentFromClass(classId,studentId);
    }

    @Override
    public int removeTeacherFromClass(Integer classId, Integer teacherId) {
        /*前端移除的学员 必定是这个班级的老师所以后端就不额外进行判断了*/
        if(classId==null){
            log.info("classId can not null");
        }
        if(teacherId==null){
            log.info("teacherId can not null");
        }
        return classesMapper.removeTeacherFromClass(classId,teacherId);
    }

    @Override
    public int removeActivityFromClass(Integer classId, Integer activityId) {
        /*前端移除的学员 必定是这个班级的活动所以后端就不额外进行判断了*/
        if(classId==null){
            log.info("classId can not null");
        }
        if(activityId==null){
            log.info("activityId can not null");
        }
        return classesMapper.removeActivityFromClass(classId,activityId);
    }

}
