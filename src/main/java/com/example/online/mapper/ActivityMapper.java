package com.example.online.mapper;

import com.example.campus.pojo.Activity;
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
public interface ActivityMapper {
    int create(Activity activity);
    int update(Activity activity);
    List<Activity> query(Activity activity);
    Activity selectById(@Param("activityId")Integer ActivityId);
}
