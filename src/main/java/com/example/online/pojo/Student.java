package com.example.online.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
public class Student {
    @JSONField(defaultValue = "0")
    private Integer id;
    @JSONField(defaultValue = "0")
    private String studentName;
    @JSONField(defaultValue = "0")
    private String fromPlace;
    @JSONField(defaultValue = "0")
    private String parentPhone;
    @JSONField(defaultValue = "0")
    private String currentGrade;
    @JSONField(defaultValue = "1")
    private Integer isFirst;
    @JSONField(defaultValue = "0")
    private Integer loginId;
    //private Integer classId;
    @JSONField(defaultValue = "0")
    private String comment;
    @JSONField(defaultValue = "0")
    private Integer status;
    @JSONField(defaultValue = "0")
    private User user;//绑定的系统用户
    @JSONField(defaultValue = "0")
    private Integer classId;//需要绑定的教学班级

}
