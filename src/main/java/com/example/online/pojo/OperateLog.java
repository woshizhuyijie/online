package com.example.online.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Data
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class OperateLog {
    private Integer id; //ID
    private Integer operateUser; //操作人ID
    private LocalDateTime operateTime; //操作时间
    private String className; //操作类名
    private String methodName; //操作方法名
    private String methodParams; //操作方法参数
    private String returnValue; //操作方法返回值
    private Long costTime; //操作耗时
}
