package com.example.online.pool;

import com.example.campus.mapper.RoleMapper;
import com.example.campus.mapper.UserMapper;
import com.example.campus.pojo.Permission;
import com.example.campus.pojo.Role;
import com.example.campus.pojo.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Component
@Slf4j
public class UserPool {//用户池
    @Autowired
    UserMapper userMapper;
   @Autowired
   RoleMapper roleMapper;
   private Map<String,List<Permission>> permissions;
   private Map<String,Integer> userNameToUserID;
   private Map<Integer,String> userIDToUserName;
   private Map<Integer,List<Role>> userIdToRoleList;
   private List<Role> roles;//系统中的所有角色
   private Map<String,Role> roleNameToRole;//系统中roleName对应于相应role

 /*  public UserPool(){
       permissions=new HashMap<>();
       userIDToUserName=new HashMap<>();
       userNameToUserID=new HashMap<>();
       userIdToRoleList=new HashMap<>();
       roleNameToRole=new HashMap<>();
       roles=roleMapper.getRoleList();
       *//*将role放进去*//*
       for (Role role:roles
            ) {
          roleNameToRole.put(role.getRoleName(),role);
       }
   }*/
    /*确保被注入后再加载role和其他信息*/
     @PostConstruct
     public void init() {
         permissions = new HashMap<>();
         userIDToUserName = new HashMap<>();
         userNameToUserID = new HashMap<>();
         userIdToRoleList = new HashMap<>();
         roleNameToRole = new HashMap<>();

         if (roleMapper != null) {
             roles = roleMapper.getRoleList();
             for (Role role : roles) {
                 roleNameToRole.put(role.getRoleName(), role);
             }
         } else {
             log.error("RoleMapper is not injected correctly.");
         }
     }
   public void addUser(User user){

       userIDToUserName.put(user.getId(),user.getLoginName());
       userNameToUserID.put(user.getLoginName(),user.getId());

       try {
           /*获取用户的role*/
           userIdToRoleList.put(user.getId(),userMapper.getRoleListByUserId(user.getId()));
           log.info(userIdToRoleList.toString());
           List<Permission> permissionList=userMapper.findPermissionsByUserId(user.getId());
           //System.out.println(permissionList);
           permissions.put(user.getLoginName(),permissionList);
       }catch (Exception e){
           e.printStackTrace();
       }

   }
    public void addUser(@Validated Integer userId){
        User user=userMapper.selectByUseID(userId);
        userIDToUserName.put(user.getId(),user.getLoginName());
        userNameToUserID.put(user.getLoginName(),user.getId());
        try {
            /*获取用户的role*/
            userIdToRoleList.put(user.getId(),userMapper.getRoleListByUserId(user.getId()));
            List<Permission> permissionList=userMapper.findPermissionsByUserId(user.getId());
            //System.out.println(permissionList);
            permissions.put(user.getLoginName(),permissionList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
   public List<Permission> getUserPerm(String userName){
       return permissions.get(userName);
   }
   public Integer getUseId(String userName){
       return userNameToUserID.get(userName);
   }
   public String getUserName(Integer userId){
       return userIDToUserName.get(userId);
   }
   public List<Role> getRoleList(Integer userId){return userIdToRoleList.get(userId);}
    /*把user信息从里面清除*/
   public void popUser(Integer userId){
       if (Objects.isNull(userId)) {
           throw new IllegalArgumentException("popUser in UserPool, userId cannot be null ");
       }
       /*移除相关信息*/
       if(permissions.containsKey(userId)){
           permissions.remove(userId);
       }
       if(userIDToUserName.containsKey(userId)) {
           if (userNameToUserID.containsKey(userIDToUserName.get(userId))) {
               userNameToUserID.remove(userIDToUserName.get(userId));
           }
           userNameToUserID.remove(userId);
       }
       if(userIdToRoleList.containsKey(userId)){
           userIdToRoleList.remove(userId);
       }
   }
   /*角色验证*/
   public boolean hasRole(String roleName,Integer userId){
       if (Objects.isNull(userId)) {
           throw new IllegalArgumentException("in hasRole function userId cannot be null");
       }
       if(roleName==null||"".equals(roleName)){
           throw new IllegalArgumentException("in hasRole function roleName cannot be null");
       }
       if(roleNameToRole.containsKey(roleName)){
           Role role=roleNameToRole.get(roleName);
           log.info(String.valueOf(role));
           log.info("roleName: {},userId: {}",roleName,userId);
          List<Role>  userRoleList=userIdToRoleList.get(userId);
          log.info("userRoleList : {}",userRoleList);
           if(Objects.isNull(userRoleList)){
               log.info("userRoleList is null");
               throw new IllegalArgumentException("in hasRole function userRoleList cannot be null");
           }
          if(userRoleList.contains(role)){
              log.info("userId {} has role {}",userId,role);
              return true;
          }else {
              log.info("role fail");
              return false;
          }
       }else return false;
   }

   //当user表 信息修改后 pool重生
    //当pool里面的信息被修改后需要重新加载数据
    public void rebirth(){

    }
}
