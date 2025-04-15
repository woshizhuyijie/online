package com.example.online.controller;

import com.example.campus.Exception.LoginException;
import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.pojo.Role;
import com.example.campus.pojo.User;
import com.example.campus.result.ResponseResult;
import com.example.campus.security.PermissionChecker;
import com.example.campus.service.serviceImpl.UserServiceImpl;
import com.example.campus.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@RestController
@Slf4j
public class loginController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    PermissionChecker permissionChecker;
    @PostMapping("/login")
    public ResponseResult<String> Login(@RequestBody User user){
        log.info("用户登录密码校验before+{}",user);
            User user1 =userServiceImpl.login(user);
            if(user1==null){
                throw new LoginException("用户名或密码错误", HttpStatusConstants.FORBIDDEN);
            }
        log.info("用户登录密码校验after+{}",user1);
        permissionChecker.putUser(user1);
        /*登录时候的身份校验*/
        boolean hasRole= permissionChecker.hasRole(user.getRole(),user1.getId());
        if (hasRole) {
            Map<String,Object> claims=new HashMap<>();//生成jwt令牌
            /*包装数据进claims*/
            claims.put("userId",user1.getId());
            String jwt= JwtUtils.generateJwt(claims);
            //List<Role> roleList=permissionChecker;
            return ResponseResult.success(jwt);
        }else{
            /*把查询出来的userId和信息pop出来*/
            permissionChecker.popUser(user1);
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"用户名或密码错误或者身份错误");
        }
    }
    @GetMapping("/test")
    @RequiresPermission("test") //权限需要test
    public ResponseResult<String> test(){
        return ResponseResult.success("成功");
    }
}
