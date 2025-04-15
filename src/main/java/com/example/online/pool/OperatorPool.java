package com.example.online.pool;

import com.example.campus.security.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Component
public class OperatorPool {
    @Autowired
    PermissionChecker permissionChecker;
    public String getOperator(){
        return permissionChecker.getOperator();
    }
}
