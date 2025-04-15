package com.example.online.mapper;



import com.example.campus.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Mapper
public interface OperateLogMapper {

    //插入日志数据

    void insert(OperateLog log);

}
