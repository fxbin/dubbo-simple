package cn.fxbin.dubbo.user.api;

import cn.fxbin.dubbo.user.dto.UserCreateDTO;
import cn.fxbin.dubbo.user.vo.UserVO;

/**
 * UserRpcService
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/6/30 13:16
 */
public interface UserRpcService {

    /**
     * 根据指定用户编号，获得用户信息
     *
     * @since 2021/7/2 17:12
     * @param id 用户编号
     * @return cn.fxbin.dubbo.user.vo.UserVO
     */
    UserVO get(Integer id);

    /**
     * 创建用户
     *
     * @since 2021/7/2 16:41
     * @param dto 创建用户对象DTO {@link UserCreateDTO}
     * @return cn.fxbin.dubbo.user.vo.UserVO
     */
    UserVO save(UserCreateDTO dto);
}
