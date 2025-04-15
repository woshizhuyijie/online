package com.example.online.security;

import com.example.campus.Exception.PermissionDeniedException;
import com.example.campus.mapper.PermissionMapper;
import com.example.campus.pojo.Permission;
import com.example.campus.pojo.Role;
import com.example.campus.pojo.User;
import com.example.campus.pool.UserPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
/**
 * 权限校验工具类
 */
@Slf4j
@Component
public class PermissionChecker {
    private List<String> operator;
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    UserPool userPool;
    public void putUser(User user){
        userPool.addUser(user);
    }
    // 模拟获取当前用户的权限
  /*  public Set<String> getCurrentUserPermissions() {
        // 假设从上下文或数据库中获取用户权限
        return Set.of("READ", "WRITE", "DELETE"); // 示例权限
    }*/
    public void putUser(Integer userId){
        if(userId==null){
            throw new PermissionDeniedException("权限校验时用户id为空",0);
        }
        userPool.addUser(userId);
    }
    // 校验用户是否拥有指定权限
    public boolean hasPermission(String requiredPermission,Integer userId) {
        //return getCurrentUserPermissions().contains(requiredPermission);
        Permission require=permissionMapper.queryByPermissionNamePermission(requiredPermission);
        List<Permission> permissionList=userPool.getUserPerm(userPool.getUserName(userId));
        log.info("执行权限校验");
        if(permissionList.contains(require)){
            return true;
        }else {
            return false;
        }
    }
    public String getUserName(Integer userId){
      return userPool.getUserName(userId);
    };
    public PermissionChecker(){
        operator=new ArrayList<>();
    }
    public void putOperator(Integer userId){
        operator.add(userPool.getUserName(userId));
    }
    public String getOperator(){//获得一次就会清空
        String name= operator.get(0);
        operator.clear();
        return name;
    }
    /*获取角色*/
    public List<Role> getRoleList(Integer userId){
        if(userId==null){
            log.info("userId can not null to get roleList");
            return null;
        }
        return userPool.getRoleList(userId);
    }
    public boolean hasRole(String roleName,Integer useId){
        return userPool.hasRole(roleName,useId);
    }
    public void popUser(User user){userPool.popUser(user.getId());}
    public  Integer getUserId(String loginName){
        if(loginName==null||"".equals(loginName)){
            log.info("loginName can not null to get roleList");
            return null;
        }
        return userPool.getUseId(loginName);
    }

}
