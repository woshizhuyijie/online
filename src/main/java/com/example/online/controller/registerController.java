package com.example.online.controller;

import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.User;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@RestController
@Slf4j
public class registerController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    /*@PostMapping("/register")

    public Result register(  @RequestBody User user){
        User user1=userServiceImpl.(user);
        if(user1 ==null){
            userServiceImpl.addUser(user);
            return Result.success();
        }else {
            return Result.error("用户已存在");
        }
    }*/
    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody User user){
        Integer ret= userServiceImpl.register(user);
        if(ret ==1){
            return ResponseResult.success("注册成功");
        }else {
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"用户已存在");
        }
    }
}
