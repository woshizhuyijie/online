package com.example.online.interceptor;


import com.example.campus.result.ResponseResult;
import com.example.campus.security.PermissionChecker;
import com.example.campus.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
   private PermissionChecker permissionChecker;
    @Override//在目标资源运行前运行 返回为true 放行 false 为拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("prehand运行了...");
        /*return HandlerInterceptor.super.preHandle(request, response, handler);*/
        //1获取url
        String url=request.getRequestURI().toString();
        log.info("拦截器login请求的url：{}",url);
        String jwt=null;
        //token为null直接不放行
        if (request.getHeader("token") != null) {
            jwt = request.getHeader("token").toString();
        } else {
            //log.info("设置重定向");
            //response.sendRedirect("/login");//设置重定向
            response.getWriter().write(ResponseResult.noLogin());
            return false;
        }
        //token为''直接不放行
        if(!StringUtils.hasLength(jwt)&&jwt!=null){
            //response.getWriter().write(Result.noLogin());//返回错误信息
            //log.info("设置重定向");
            //response.sendRedirect("/login");//设置重定向
            response.getWriter().write(ResponseResult.noLogin());
            return false;
        }
        try{
            Claims claims= JwtUtils.parseJWT(jwt);//捕获解析异常 失败就不放行 成功放行
            //把登录的id 加入
            //用户id
            Integer userId= (Integer) claims.get("userId");
            log.info("claims is {}",claims);
            log.info("put userId:{}",userId);
            //加入校验系统 重复会将key 替换掉
            permissionChecker.putUser(userId);
        }catch (Exception e){
            e.printStackTrace();
            response.getWriter().write(ResponseResult.noLogin());//返回错误信息
           // response.getWriter().write(e.getMessage());//
            return false;
        }
        log.info("放行");
        return true;
    }

    @Override//在目标资源运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("posthand运行了...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override//视图渲染后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterhand运行了...");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
