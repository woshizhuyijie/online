package com.example.online.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class Teacher {
    @JSONField(defaultValue = "0")
    private Integer id;
    @JSONField(defaultValue = "0")
    private String teacherName;
    @JSONField(defaultValue = "0")
    private String subject;
    @JSONField(defaultValue = "0")
    private String profession;
    @JSONField(defaultValue = "0")
    private String introduce;
    @JSONField(defaultValue = "0")
    private Integer loginId;
    @JSONField(defaultValue = "0")
    private List<Course> courseList;
    @JSONField(defaultValue = "0")
    private String schoolName;
    @JSONField(defaultValue = "0")
    private User user;//绑定的系统用户
    @JSONField(defaultValue = "0")
    private Integer status;
}
