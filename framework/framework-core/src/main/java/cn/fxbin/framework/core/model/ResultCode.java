package cn.fxbin.framework.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResultCode
 *
 * -1 ~ 999 系统异常
 *
 * 0 标识成功， -1 为默认失败状态码
 *
 * 其它：
 * 一般情况下，使用 HTTP 响应状态码 <a href="#">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status</a>
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 11:56
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 接口调用成功
     */
    SUCCESS(0, "Request Successful"),

    /**
     * 服务器暂不可用，建议稍候重试。建议重试次数不超过3次。
     */
    FAILURE(-1, "System Busy"),

    /**
     * 请求参数有误
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * 找不到地址
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * 消息不能读取
     */
    MESSAGE_NOT_READABLE(405,"Message Not Readable"),

    /**
     * 不接受的媒体类型
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * 服务异常
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")

    ;



    final int code;

    final String errmsg;
}

