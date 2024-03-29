package cn.fxbin.framework.core.logging;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * SysExceptionLogDTO
 *
 * @author fxbin
 * @version v1.0
 * @since 2021/7/4 11:42
 */
@Data
@Accessors(chain = true)
public class SysExceptionLogDTO implements Serializable {

    private static final long serialVersionUID = -5534998372406306243L;
    /**
     * 账号编号
     */
    private Integer userId;
    /**
     * 用户类型
     *
     * 枚举
     */
    private Integer userType;

    /**
     * 链路编号
     */
    private String traceId;

    @NotNull(message = "应用名不能为空")
    private String application;

    @NotNull(message = "访问地址不能为空")
    private String uri;

    @NotNull(message = "请求参数不能为空")
    private String queryString;

    @NotNull(message = "http 请求方法不能为空")
    private String method;

    @NotNull(message = "User-Agent 不能为空")
    private String userAgent;

    @NotNull(message = "ip 不能为空")
    private String ip;

    @NotNull(message = "异常时间不能为空")
    private Date exceptionTime;

    @NotNull(message = "异常名不能为空")
    private String exceptionName;

    @NotNull(message = "异常发生的类全名不能为空")
    private String exceptionClassName;

    @NotNull(message = "异常发生的类文件不能为空")
    private String exceptionFileName;

    @NotNull(message = "异常发生的方法名不能为空")
    private String exceptionMethodName;

    @NotNull(message = "异常发生的方法所在行不能为空")
    private Integer exceptionLineNumber;

    @NotNull(message = "异常的栈轨迹不能为空")
    private String exceptionStackTrace;

    @NotNull(message = "异常导致的根消息不能为空")
    private String exceptionRootCauseMessage;

    @NotNull(message = "异常导致的消息不能为空")
    private String exceptionMessage;


}
