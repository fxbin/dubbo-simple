package cn.fxbin.dubbo.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * UserVO
 *
 * @author fxbin
 * @version 1.0
 * @since 2021/6/30 13:15
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 7785900181461391298L;

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @Length(min = 5, max = 16, message = "账号长度为 5-16 位")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

}
