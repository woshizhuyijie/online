package com.example.online.mapper;

import com.example.campus.pojo.Permission;
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
public interface PermissionMapper{
    // 多条件创建权限
    int createPermission(Permission permission);

    // 多条件查询权限
    List<Permission> queryPermissions(Permission permission);

    // 多条件更新权限
    int updatePermission(Permission permission);
    //role 对permission 一对多绑定
    int bindPermissionForRoleByRoleId(@Param("permissionId") Integer permissionId,@Param("roleId") Integer roleId);

    //查询role 权限
    List<Permission> queryPermissionsByRoleId(@Param("roleId") Integer roleId);
    //
    Permission queryByPermissionNamePermission(@Param("permissionName") String permissionName);
}
