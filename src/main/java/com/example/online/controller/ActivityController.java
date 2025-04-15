package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.mapper.ActivityMapper;
import com.example.campus.pojo.Activity;
import com.example.campus.pool.OperatorPool;
import com.example.campus.result.ResponseResult;
import com.example.campus.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@RestController
@Slf4j
@RequestMapping("/activity")
public class ActivityController {
      @Autowired
      ActivityService activityService;
      @Autowired
      OperatorPool operatorPool;

      @PostMapping("/query")
      @RequiresPermission("activity")
      @Cacheable(cacheNames = "campus:activity:query", key = "'activitylist'")
      public ResponseResult<Object> query(@RequestBody Activity activity){
          List<Activity> activityList=activityService.query(activity);
          return ResponseResult.success(activityList);
      };
      @PostMapping("/postStatus")
      @RequiresPermission("activity")
      public ResponseResult<String> postStatus(@RequestBody Activity activity){
          log.info(activity.toString());
          int ret=activityService.postActivity(activity);
          System.out.println(ret);
          if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"发布失败,activity不存在");
          else return ResponseResult.success("成功发布");
      };
      @PostMapping("/update")
      @RequiresPermission("activity")
      @CacheEvict(value = "campus:activity:query", allEntries = true)  // 插入数据时清空缓存
    public ResponseResult<String> updateActivity(@RequestBody Activity activity){
          int ret=activityService.update(activity);
          if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"修改失败,activity不存在");
          else return ResponseResult.success("修改成功");
      }
    @PostMapping("/create")
    @RequiresPermission("activity")
    @CacheEvict(value = "campus:activity:query", allEntries = true)
    public ResponseResult<String> createActivity(@RequestBody Activity activity){
          String opt=operatorPool.getOperator();
          activity.setCreator(opt);
          int ret=activityService.create(activity);
        if(ret==0) return ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"活动创建失败");
        else return ResponseResult.success("创建成功");

     }
}
