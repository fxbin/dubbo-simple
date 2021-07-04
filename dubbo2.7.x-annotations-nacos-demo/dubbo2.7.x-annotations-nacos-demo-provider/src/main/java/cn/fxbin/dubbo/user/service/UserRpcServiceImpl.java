package cn.fxbin.dubbo.user.service;

import cn.fxbin.dubbo.user.api.UserRpcService;
import cn.fxbin.dubbo.user.convert.UserConvert;
import cn.fxbin.dubbo.user.dto.UserCreateDTO;
import cn.fxbin.dubbo.user.vo.UserVO;
import cn.hutool.core.util.RandomUtil;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * UserRpcServiceImpl
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/4 10:37
 */
@DubboService(version = "${dubbo.provider.UserRpcService.version}")
public class UserRpcServiceImpl implements UserRpcService {


    /**
     * 根据指定用户编号，获得用户信息
     *
     * @param id 用户编号
     * @return cn.fxbin.dubbo.user.vo.UserVO
     * @since 2021/7/4 10:37
     */
    @Override
    public UserVO get(Integer id) {
        return UserVO.builder().id(id).gender(1).name("name" + id).build();
    }

    /**
     * 创建用户
     *
     * @param dto 创建用户对象DTO {@link UserCreateDTO}
     * @return cn.fxbin.dubbo.user.vo.UserVO
     * @since 2021/7/4 10:37
     */
    @Override
    public UserVO save(UserCreateDTO dto) {
        return UserConvert.INSTANCE.convert(dto).setId(RandomUtil.getRandom().nextInt());
    }
}
