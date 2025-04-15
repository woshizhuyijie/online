package com.example.campus.service;

import com.example.campus.pojo.Activity;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface ActivityService {
   int create(Activity activity);
   int postActivity(Activity activity);
   List<Activity> query(Activity activity);
   Activity selectByActivityId(Integer activityId);
   int update(Activity activity);
}
