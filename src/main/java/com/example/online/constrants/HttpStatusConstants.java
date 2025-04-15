package com.example.online.constrants;

/**
 * HTTP 状态码常量类
 * 定义常用的 HTTP 状态码，以便在项目中统一使用。
 */
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public class HttpStatusConstants {

    // 1xx: Informational
    public static final int CONTINUE = 100;                  // 请求继续
    public static final int SWITCHING_PROTOCOLS = 101;       // 切换协议

    // 2xx: Success
    public static final int OK = 200;                       // 请求成功
    public static final int CREATED = 201;                  // 创建成功
    public static final int ACCEPTED = 202;                 // 请求已接受
    public static final int NO_CONTENT = 204;               // 无内容

    // 3xx: Redirection
    public static final int MOVED_PERMANENTLY = 301;         // 资源永久移动
    public static final int FOUND = 302;                    // 资源临时移动
    public static final int NOT_MODIFIED = 304;             // 资源未修改

    // 4xx: Client Error
    public static final int BAD_REQUEST = 400;              // 错误的请求
    public static final int UNAUTHORIZED = 401;             // 未授权
    public static final int FORBIDDEN = 403;                // 禁止访问
    public static final int NOT_FOUND = 404;                // 未找到资源
    public static final int METHOD_NOT_ALLOWED = 405;       // 方法不被允许
    public static final int CONFLICT = 409;                 // 资源冲突
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;   // 不支持的媒体类型

    // 5xx: Server Error
    public static final int INTERNAL_SERVER_ERROR = 500;    // 服务器内部错误
    public static final int NOT_IMPLEMENTED = 501;          // 未实现
    public static final int BAD_GATEWAY = 502;              // 错误网关
    public static final int SERVICE_UNAVAILABLE = 503;      // 服务不可用
    public static final int GATEWAY_TIMEOUT = 504;          // 网关超时

    // 私有构造方法，防止实例化
    private HttpStatusConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
