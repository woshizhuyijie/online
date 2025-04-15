package com.example.online.ExceptionHandler;

import com.example.campus.Exception.LoginException;
import com.example.campus.Exception.PermissionDeniedException;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.result.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获权限异常
     */
   /* @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(PermissionDeniedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
    }*/
    /*@ExceptionHandler(PermissionDeniedException.class)
    public ResponseResult<Void> handlerPermissionDeniedException(PermissionDeniedException ex){
        return ResponseResult.failure(ex.getStatus(),ex.getMessage());
    }*/
    /**
     * 捕获其他未处理的异常
     */
   /* @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleGenericException(Exception ex) {
        //ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        //return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseResult.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }*/
    /*
    * 登录异常
    * */
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public ResponseResult<Void> handleCustomException(LoginException e) {
        return ResponseResult.failure(HttpStatusConstants.UNAUTHORIZED, e.getMessage());
    }
    /**
     * 捕获自定义权限异常
     */
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseBody
    public ResponseResult<Void> handleCustomException(PermissionDeniedException e) {
        return ResponseResult.failure(HttpStatusConstants.UNAUTHORIZED, e.getMessage());
    }
    /**
     * 捕获通用异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<Void> handleException(Exception e) {
        return ResponseResult.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


}
