package com.example.online.mapper;

import com.example.campus.pojo.Role;
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
public interface RoleMapper {
    // 多条件创建角色
    int createRole(Role role);

    // 多条件查询角色
    List<Role> queryRoles(Role role);

    // 多条件更新角色
    int updateRole(Role role);
    //role 对permission 一对多绑定和查询未写
    //user 对 role一对多绑定和查询未写

    // user 权限查询未写  已经写了
    //查询role
    List<Role> queryRolesByUserId(@Param("userId") Integer userId);
    // 为user 绑定role
    int bindRoleForUserByUserId(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
    /*user pool 在初始化时获取所有的role*/
    List<Role> getRoleList();
}
