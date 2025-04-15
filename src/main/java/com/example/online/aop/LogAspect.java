package com.example.online.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.campus.mapper.OperateLogMapper;
import com.example.campus.pojo.OperateLog;
import com.example.campus.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Aspect
@Component
@Slf4j
public class LogAspect {
    //切点表达式
    @Pointcut("@annotation(com.example.campus.annotation.Log))")
    public void advice(){};

    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //定义环绕通知
    @Around("advice()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {


        //获取令牌解析令牌
        String jwt=httpServletRequest.getHeader("token");
        Claims claims= JwtUtils.parseJWT(jwt);

        //解析数据
        //操作人
        Integer operateUser= (Integer) claims.get("id");

        //操作时间
        LocalDateTime operateTime=LocalDateTime.now();

        //操作类名
        String className=joinPoint.getTarget().getClass().getName();

        //方法名
        String methodName=joinPoint.getSignature().getName();

        //操作参数
        String methodParams= Arrays.toString(joinPoint.getArgs());


        //调用原始目标方法
        Long begin=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        Long end=System.currentTimeMillis();
        //操作返回值 没有返回值可能因为切入点是接口实现类无返回值
        String returnValue= JSONObject.toJSONString(result);

        //操作耗时 毫秒
        Long costTime=end-begin;

        OperateLog operateLog=new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);
        log.info("在aop中记录操作日志");
        return result;
    }
}
