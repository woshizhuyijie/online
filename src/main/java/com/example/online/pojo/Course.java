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
public class Course {
    @JSONField(defaultValue = "0")
    private Integer courseId;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime createdTime;
    @JSONField(defaultValue = "0")
    private Integer status;
    @JSONField(defaultValue = "0")
    private List<Activity> activityList;
    @JSONField(defaultValue = "0")
    private List<com.example.campus.pojo.Teacher> teacherList;
    @JSONField(defaultValue = "0")
    private List<Classes> classesList;
    @JSONField(defaultValue = "0")
    private String courseName;
    private List<com.example.campus.pojo.TeachingUnit> unitList;//教学单元列表
}
