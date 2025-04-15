package com.example.online.service.serviceImpl;


import com.example.campus.service.ActivityService;
import com.example.campus.utils.EmptyUtils;
import com.example.online.constrants.CampusConstraints;
import com.example.online.mapper.ActivityMapper;
import com.example.online.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Override
    public int create(Activity activity) {
        if(EmptyUtils.emptyForString(activity.getCategory(), "category can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForString(activity.getName(),"activityName can not null")==0){
            return 0;
        }
        if(EmptyUtils.emptyForString(activity.getCreator(),"activityCreator can not null")==0){
            return 0;
        }
        activity.setCreatedTime(LocalDateTime.now());

        return activityMapper.create(activity);
    }

    @Override
    public int postActivity(Activity activity) {
        if(EmptyUtils.emptyForObject(activity.getId(),"activityId can not null")==0){
            return 0;
        }
        return activityMapper.update(activity.setStatus(CampusConstraints.Activity_status_published).setUpdateTime(LocalDateTime.now()));
    }

    @Override
    public List<Activity> query(Activity activity) {
        return activityMapper.query(activity);
    }

    @Override
    public Activity selectByActivityId(Integer activityId) {
        if(EmptyUtils.emptyForObject(activityId,"activityId can not null")==0){
            return null;
        }
        return activityMapper.selectById(activityId);
    }

    @Override
    public int update(Activity activity) {
        if(EmptyUtils.emptyForObject(activity.getId(),"activityId can not null")==0){
            return 0;
        }
        return activityMapper.update(activity.setUpdateTime(LocalDateTime.now()));
    }
}
