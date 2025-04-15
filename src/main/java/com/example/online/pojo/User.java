package com.example.online.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    @JSONField(defaultValue = "0")
    private Integer id;
    @JSONField(defaultValue = "0")
    private String loginName;
    @JSONField(defaultValue = "0")  // 设置默认值
    private Integer gender;
    @JSONField(format = "yyyy-MM-dd ")  // 配置日期格式
    private LocalDateTime birthday;
    @JSONField(defaultValue = "0")  // 设置默认值
    private String phone;
    @JSONField(defaultValue = "null")  // 设置默认值
    private String email;
    @JSONField(defaultValue = "0")  // 设置默认值
    private Integer status;
    @JSONField(defaultValue = "null")  // 设置默认值
    private List<Role> roleList=new ArrayList<>();
    @JSONField(defaultValue = "can't show")  // 设置默认值
    private String password="null";
    @JSONField(defaultValue = "0")  // 设置默认值
    private Integer isBind;
    private String role;//登录时候的身份
}
