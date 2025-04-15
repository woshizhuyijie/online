package com.example.online.result;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.example.campus.constrants.HttpStatusConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.LocalDateTime;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseResult<T> {
    private Integer code;      // 状态码
    private String message;    // 返回消息
    private T data;            // 数据内容
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")  // fastjson 格式化 LocalDateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // jackson 格式化 LocalDateTime
    private LocalDateTime timestamp;  // 时间戳

    // 构造方法
    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // 快速构建成功结果
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "Success", data);
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(200, message, data);
    }

    // 快速构建失败结果
    public static <T> ResponseResult<T> failure(Integer code, String message) {
        return new ResponseResult<>(code, message, null);
    }

    public static <T> ResponseResult<T> failure(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }

    // Getter 和 Setter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // 静态方法返回 JSON 字符串
    public static String noLogin(){
        ResponseResult<String> error = ResponseResult.failure(HttpStatusConstants.FORBIDDEN,"NO_LOGIN");
        return JSONObject.toJSONString(error);
    }
}

