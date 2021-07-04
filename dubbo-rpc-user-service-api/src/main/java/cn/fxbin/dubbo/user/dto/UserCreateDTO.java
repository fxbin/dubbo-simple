package cn.fxbin.dubbo.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * UserCreateDTO
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/7/2 16:40
 */
@Data
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 3379263024653084547L;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别，0 女，1 男
     */
    private Integer gender;

}
