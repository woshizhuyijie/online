package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.User;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@RestController
@Slf4j
@RequestMapping("/user")
public class userController {//用户管理
    @Autowired
    UserService userService;

    @PostMapping("/query")
    @RequiresPermission("root")
    @Cacheable(cacheNames = "campus:users:query", key = "'userlist'")
    public ResponseResult<Object> getUsers(@RequestBody User user){
        List<User> users = userService.query(user);
        return ResponseResult.success(users);
    }
    @PostMapping("/update")
    @RequiresPermission("root")
    @CacheEvict(value = "campus:users:query", allEntries = true)  // 插入数据时清空缓存
    public ResponseResult<String> updateUser(@RequestBody User user){
        int ret=userService.update(user);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"修改失败,user不存在");
        else return ResponseResult.success("修改成功");
    }
    @PostMapping("/getUserInfo")
    @RequiresPermission("user")
    public  ResponseResult<Object> getUserInfo(@RequestBody User user){
        /*可能会出现别的用户查询其他用户信息*/
        String userName=user.getLoginName();
        User userInfo=userService.selectByUserName(user);
        return ResponseResult.success(userInfo);
    }
}
