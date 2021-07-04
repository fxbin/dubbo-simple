package cn.fxbin.dubbo.user.controller;

import cn.fxbin.dubbo.user.api.UserRpcService;
import cn.fxbin.dubbo.user.dto.UserCreateDTO;
import cn.fxbin.framework.core.exception.ServiceException;
import cn.fxbin.framework.core.model.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ServerCloneException;

/**
 * UserController
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/4 10:38
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference(version = "${dubbo.consumer.UserRpcService.version}")
    private UserRpcService userRpcService;

    @ApiOperation("查询")
    @GetMapping("/{userId}")
    public Result<?> get(@PathVariable("userId") Integer userId) {
        return Result.success(userRpcService.get(userId));
    }

    @ApiOperation("新增用户")
    @PostMapping
    public Result<?> save(@RequestBody UserCreateDTO request) {
        return Result.success(userRpcService.save(request));
    }

    @ApiOperation("exception handler 测试")
    @GetMapping("/handler")
    public Result<?> handler() {
        throw new ServiceException("测试异常处理。。。");
    }

}
