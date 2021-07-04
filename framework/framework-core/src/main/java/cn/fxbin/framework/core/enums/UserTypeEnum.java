package cn.fxbin.framework.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UserTypeEnum
 *
 * @author fxbin
 * @version v1.0
 * @since 2021/7/4 11:46
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    /**
     *
     */
    USER(1, "用户"),

    ADMIN(2, "管理员");

    /**
     * 类型
     */
    private final Integer value;

    /**
     * 类型名
     */
    private final String name;
}
