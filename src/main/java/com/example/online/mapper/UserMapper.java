package com.example.online.mapper;

import com.example.campus.pojo.Permission;
import com.example.campus.pojo.Role;
import com.example.campus.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Mapper
public interface UserMapper {
    int registerUser(User user);
    User login(User user);//以及弃用 不安全的登录
    List<User> query(User user);
    int update(User user);

    // 根据用户 ID 查询权限
    List<Permission> findPermissionsByUserId(@Param("userId") Integer userId);
    String getLoginNameString(@Param("userId") Integer userId);
    User selectByUseID(@Param("userId") Integer userId);
    String getPasswordString(@Param("loginName") String loginName);
    User getByUserName(@Param("loginName") String loginName);
    int getUserIdByUserName(@Param("loginName") String loginName);
    List<Role> getRoleListByUserId(@Param("userId") Integer userId);
}
