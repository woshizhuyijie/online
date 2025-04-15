package com.example.online.service.serviceImpl;


import com.example.campus.utils.MD5Utils;
import com.example.online.mapper.RoleMapper;
import com.example.online.mapper.TeacherMapper;
import com.example.online.mapper.UserMapper;
import com.example.online.pojo.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.example.campus.utils.EmptyUtils.throwSQlE;
import static com.example.campus.utils.UsernameGenerator.generateUsername;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */

@Service
@Slf4j

public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Override
    @Transactional
    public int insertTeacher(Teacher teacher) {//添加教师时同时为教师创建系统账号
        if(teacher.getTeacherName()==null || "".equals(teacher.getTeacherName())){
            log.info("teacherName can not null");
            return 0;
        }

        String loginName="teacher"+teacherMapper.countTeacher()+teacher.getTeacherName();
        User rUser=userMapper.getByUserName(loginName);//查询是否可能存在重复的用户名
        User userNew=new User();
        if(rUser!=null){//如果还是重复就加一个uuid
            try{
                String userNameHAS=generateUsername(loginName);
                userNew.setLoginName("teacher"+userNameHAS);
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
            if(r1==0)  throwSQlE("TeacherServiceImpl userMapper.registerUser fail");
            int useId=userMapper.getUserIdByUserName(userNew.getLoginName());
            /*插入学生 并且绑定系统用户*/
            int r2=teacherMapper.insertTeacher(teacher.setLoginId(useId));
            if(r2==0 ) throwSQlE("TeacherServiceImpl TeacherMapper.insertTeacher fail");
            /*绑定role 3是代表Teacher*/
            int r3=roleMapper.bindRoleForUserByUserId(useId,3);
            if(r3==0) throwSQlE("TeacherServiceImpl roleMapper.bindRoleForUserByUserId fail");
        }catch (SQLException e){
            log.info("insertTeacher fail");
            return 0;//执行失败
        }
        return 1;//执行成功
        //return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public List<Teacher> query(Teacher teacher) {
        return teacherMapper.queryTeachers(teacher);
    }

    @Override
    public int update(Teacher teacher) {
        if(teacher.getId()==null){
            log.info("teacher Id can not null");
            return 0;
        }
       return teacherMapper.updateTeacher(teacher);
    }

}
