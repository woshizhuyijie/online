package com.example.online.annotation;

import java.lang.annotation.*;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    String value(); // 权限标识
}
