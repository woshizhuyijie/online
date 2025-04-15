package com.example.online.aop;

import com.example.campus.Exception.PermissionDeniedException;
import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.security.PermissionChecker;
import com.example.campus.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
/**
 * 权限校验切面
 */
@Aspect
@Component
@Slf4j
public class PermissionAspect {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    PermissionChecker permissionChecker;
    /**
     * 拦截带有 @RequiresPermission 注解的方法
     */
    @Around("@annotation(com.example.campus.annotation.RequiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取令牌解析令牌
        String jwt=httpServletRequest.getHeader("token");
        Claims claims= JwtUtils.parseJWT(jwt);

        //获取用户信息
        //用户id
        Integer userId= (Integer) claims.get("userId");
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取注解中的权限标识
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        String requiredPermission = requiresPermission.value();

        // 校验权限
        if (!permissionChecker.hasPermission(requiredPermission,userId)) {
            throw new PermissionDeniedException("Access Denied: You don't have permission to perform this action.", HttpStatusConstants.FORBIDDEN);
        }
        log.info("权限校验通过用户id{}",userId);
        // 执行目标方法
        permissionChecker.putOperator(userId);
        Object result = joinPoint.proceed();
        // 权限校验通过，继续执行目标方法



        return result;
    }
}
