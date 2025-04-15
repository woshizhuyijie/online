package com.example.online.config;


import com.example.campus.interceptor.LoginCheckInterceptor;
import com.example.campus.interceptor.NorsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Configuration//配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    @Bean
    NorsInterceptor getNorsInterceptor() {
        return new NorsInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*跨域拦截器*/
        registry.addInterceptor(getNorsInterceptor()).addPathPatterns("/**");
        //添加拦截器 /** 拦截所有资源 先不拦截
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/login")
                .excludePathPatterns("/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
