package com.example.online.Exception;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public class PermissionDeniedException extends RuntimeException {
    private final Integer status;

    public PermissionDeniedException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
