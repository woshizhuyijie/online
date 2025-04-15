package com.example.campus;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
//打成war包
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CampusApplication.class);
    }

}
