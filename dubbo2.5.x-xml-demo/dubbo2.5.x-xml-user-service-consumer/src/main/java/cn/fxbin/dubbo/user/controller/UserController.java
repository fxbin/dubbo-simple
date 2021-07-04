package cn.fxbin.dubbo.user.controller;

import cn.fxbin.framework.core.model.Result;
import cn.fxbin.dubbo.user.api.UserRpcService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 11:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRpcService userRpcService;

    @ApiOperation("get 方法")
    @GetMapping("/{userId}")
    public Result<?> get(@PathVariable("userId") Integer userId) {
        return Result.success(userRpcService.get(userId));
    }


}
