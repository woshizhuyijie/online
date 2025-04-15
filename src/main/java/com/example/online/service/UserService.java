package com.example.online.service;


import com.example.online.pojo.User;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface UserService {
    int register(User user);
    User login(User user);
    List<User> query(User user);
    int update(User user);
    User selectByUserName(User user);
}
