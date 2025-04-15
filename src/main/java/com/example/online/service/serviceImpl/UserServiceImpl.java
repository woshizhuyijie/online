package com.example.online.service.serviceImpl;


import com.example.campus.utils.MD5Utils;
import com.example.online.mapper.UserMapper;
import com.example.online.pojo.User;
import com.example.online.security.PermissionChecker;
import com.example.online.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionChecker permissionChecker;
    @Override
    public int register(User user) {
        if(user.getLoginName()==null||"".equals(user.getLoginName())){
            log.info("loginName can not be null");
            return 0;
        }
        if(user.getPassword()==null||"".equals(user.getPassword())){
            log.info("password can not be null");
            return 0;
        }
        // 判断用户名是否已经注册了
        List<User> users=userMapper.query(new User().setLoginName(user.getLoginName()));
        if(users==null || users.isEmpty()){
            user.setPassword(MD5Utils.md5Encoder(user.getPassword(),user.getLoginName()));
            user.setStatus(1);
           return userMapper.registerUser(user);
        }else {
            log.info("userName already  be registered");
            return 0;
        }
    }

    @Override
    public User login(User user) {
        if(user.getLoginName()==null||"".equals(user.getLoginName())){
            log.info("loginName can not be null");
            return null;
        }
        if(user.getPassword()==null||"".equals(user.getPassword())){
            log.info("password can not be null");
            return null;
        }
        /*前端已经进行加密这里不需要再加密了*/
        //user.setPassword(MD5Utils.md5Encoder(user.getPassword(),user.getLoginName()));
        String passwordUser=userMapper.getPasswordString(user.getLoginName());
        log.info("p1+{}"+user.getPassword());
        log.info("p2+{}",passwordUser);
        //取出密码进行比对
        if(passwordUser!=null&&!"".equals(passwordUser)&&user.getPassword().equals(passwordUser)){
            return userMapper.getByUserName(user.getLoginName());
        }else return null;
        //return userMapper.login(user);
    }

    @Override
    public List<User> query(User user) {
        return userMapper.query(user);
    }

    @Override
    public int update(User user) {
        if(user.getLoginName()==null||"".equals(user.getLoginName())){
            log.info("loginName can not be null");
            return 0;
        }
        return userMapper.update(user);
    }

    @Override
    public User selectByUserName(User user) {
        if(user.getLoginName()==null||"".equals(user.getLoginName())){
            log.info("loginName can not be null");
            return null;
        }
        Integer userId=permissionChecker.getUserId(user.getLoginName());
        User userInfo=userMapper.selectByUseID(userId);
        userInfo.setPassword(null);
        return userInfo;
    }
}
