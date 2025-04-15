package com.example.campus.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */


@Slf4j
public class EmptyUtils {
    public static final int notNull = 1;
    public static final int Null = 0;

    public static int emptyForString(String property, String log) {
        if (property == null || "".equals(property)) {
            System.out.println(log);
            return Null;
        } else {
            return notNull;
        }
    }


    public static int emptyForObject(Object property, String log) {
        if (property == null) {
            System.out.println(log);
            return Null;
        } else {
            return notNull;
        }
    }
    /*抛出异常回滚事物*/
    public static void throwSQlE(String msg) throws SQLException {
        log.info("throwSQLE:{}"+msg);
        throw new SQLException();
    }
}
