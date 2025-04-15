package com.example.online.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
public class Classes {
    @JSONField(defaultValue = "0")
    private Integer classId;
    @JSONField(defaultValue = "0")
    private String className;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime createdTime;
    @JSONField(defaultValue = "0")
    private String classDescription;//教学班描述
    @JSONField(defaultValue = "0")
    private List<com.example.campus.pojo.Teacher> teacherList;
    @JSONField(defaultValue = "0")
    private List<Activity> activityList;
    @JSONField(defaultValue = "0")
    private com.example.campus.pojo.Course course;//教学班对应的课程
    @JSONField(defaultValue = "0")
    private List<com.example.campus.pojo.Student> studentList;//教学班的学生名单
}
