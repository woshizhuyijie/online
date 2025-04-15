package com.example.online.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
public class Activity {
    @JSONField(defaultValue = "0")
    private String name;
    @JSONField(defaultValue = "0")
    private String title;
    @JSONField(defaultValue = "0")
    private String creator;
    @JSONField(defaultValue = "0")
    private Integer id;
    @JSONField(defaultValue = "0")
    private Integer status;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime startTime;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime endTime;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime createdTime;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime archivedTime;
    @JSONField(defaultValue = "0")
    private String pictureUrl;
    @JSONField(defaultValue = "0")
    private String abstractContent;
    @JSONField(defaultValue = "0")
    private String category;
    @JSONField(defaultValue = "0")
    private String detailedDescription;
    @JSONField(format = "yyyy-MM-dd ")
    private LocalDateTime updateTime;
}
