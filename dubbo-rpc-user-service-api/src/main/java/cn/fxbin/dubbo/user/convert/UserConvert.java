package cn.fxbin.dubbo.user.convert;

import cn.fxbin.dubbo.user.dto.UserCreateDTO;
import cn.fxbin.dubbo.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * UserConvert
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 16:44
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(target = "id", ignore = true)
    UserVO convert(UserCreateDTO bean);

}
