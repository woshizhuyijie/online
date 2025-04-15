package com.example.online.service.serviceImpl;


import com.example.campus.service.StudentService;
import com.example.campus.service.UserService;
import com.example.campus.utils.EmptyUtils;
import com.example.campus.utils.MD5Utils;
import com.example.online.mapper.ClassesMapper;
import com.example.online.mapper.RoleMapper;
import com.example.online.mapper.StudentMapper;
import com.example.online.mapper.UserMapper;
import com.example.online.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.example.campus.utils.EmptyUtils.throwSQlE;
import static com.example.campus.utils.UsernameGenerator.generateUsername;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassesMapper classesMapper;
    @Autowired
    RoleMapper roleMapper;
    @Override
    @Transactional(rollbackFor = SQLException.class)
    public int addStudent(Student student)  {//添加学员时同时为学员系统创建账号 创建学员现在不需要classId了
         if(EmptyUtils.emptyForString(student.getStudentName(),"name can not null")==0){
             return 0;
         }
        if(EmptyUtils.emptyForString(student.getFromPlace(),"fromPlace can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForString(student.getCurrentGrade(),"grade can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForObject(student.getIsFirst(),"isFirst can not null")==0){
            return 0;
        }
        /*if(EmptyUtils.emptyForObject(student.getClassId(),"classId can not null")==0){
            return 0;
        }*/
       /* Classes classes=classesMapper.selectByClassId(student.getClassId());//获取这个classId是否存在
        if(EmptyUtils.emptyForObject(classes,"class is not exist")==0){
            return 0;
        }*/
        String loginName="stu"+studentMapper.countNumber()+student.getStudentName();
        User rUser=userMapper.getByUserName(loginName);//查询是否可能存在重复的用户名
        User userNew=new User();
        if(rUser!=null){//如果还是重复就加一个uuid
           try{
              String userNameHAS=generateUsername(loginName);
              userNew.setLoginName("stu"+userNameHAS);
              log.info("exist loginName same generate new UseName+{}",userNameHAS);
           }catch (Exception e){

           }
        }else{
            userNew.setLoginName(loginName);
        }
        /*设置默认密码 md加密*/
        userNew.setPassword(MD5Utils.md5Encoder("123456",userNew.getLoginName()));
        userNew.setIsBind(1).setStatus(1);//设置状态
        /*插入系统用户*/
        try{


        int r1= userMapper.registerUser(userNew);//设置为已经绑定
        if(r1==0)  throwSQlE("sutdentServiceImpl userMapper.registerUser fail");
        int useId=userMapper.getUserIdByUserName(userNew.getLoginName());
        /*插入学生 并且绑定系统用户*/
        int r2=studentMapper.insertStudent(student.setLoginId(useId));
        if(r2==0 ) throwSQlE("sutdentServiceImpl studentMapper.insertStudent fail");
        /*绑定role 5是代表student*/
        int r3=roleMapper.bindRoleForUserByUserId(useId,5);
        if(r3==0) throwSQlE("sutdentServiceImpl roleMapper.bindRoleForUserByUserId fail");
        /*学生绑定教学班 学生插入成功之后会自动把id 赋值给student*/

       /* int r4=classesMapper.bindStudentForClass(student.getClassId(),student.getId());
        if(r4==0) throwSQlE("sutdentServiceImpl classesMapper.bindStudentForClass fail");*/
        }catch (SQLException e){
            log.info("insertStudent fail");
            return 0;//执行失败
        }
        return 1;//执行成功
    }

    @Override
    public int updateStudent(Student student) {
        if(EmptyUtils.emptyForObject(student.getId(),"studenId can not null")==0){
            return 0;
        }
        return studentMapper.updateStudent(student);
    }
    //需要验证班级是否存在 废弃了 在classService中写了个新方法
    //@Override
    /*public int bindClass(Student student) {
        if(EmptyUtils.emptyForObject(student.getId(),"studenId can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForObject(student.getClassId(),"classId can not null")==0){
            return 0;
        }
        List<Classes> classesList=classesMapper.queryClasses(new Classes().setClassId(student.getClassId()));
        if(classesList==null || classesList.isEmpty()){
            log.info("class can not be found by classId");
            return 0;
        }

        return studentMapper.updateStudent(student);
    }*/
    //需要验证用户是否被绑定
    @Transactional
    @Override//废弃了
    public int bindUser(Student student) {
        if(EmptyUtils.emptyForObject(student.getId(),"studenId can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForObject(student.getLoginId(),"studenId can not null")==0){
            return 0;
        }
        User user=userMapper.selectByUseID(student.getLoginId());
        if(user==null){
            log.info("用户不存在");
            return 0;
        }
        List<User> userList=userMapper.query(new User().setId(student.getLoginId()).setIsBind(Integer.valueOf(0)));
        if(userList==null||userList.isEmpty()){
            log.info("user 已经被绑定了");
            return 0;
        }
        userMapper.update(user.setIsBind(Integer.valueOf(1)));
        return studentMapper.updateStudent(student);
    }
    //为空查询全部
    @Override
    public List<Student> query(Student student) {
        return studentMapper.queryStudents(student);
    }

    @Override
    public int getStuIdByUseId(Integer userId) {
        if(userId==null){
            log.info("userId can not null");
            return 0;
        }
        User user=userMapper.selectByUseID(userId);
        if(user==null){
            log.info("用户不存在");
            return 0;
        }
        return studentMapper.getStuIdByLoginId(userId);
    }

}
