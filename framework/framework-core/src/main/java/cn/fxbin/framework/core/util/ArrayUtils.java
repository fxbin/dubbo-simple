package cn.fxbin.framework.core.util;

import cn.fxbin.framework.core.exception.UtilException;
import cn.hutool.core.util.ArrayUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * ArrayUtils
 *
 * @author fxbin
 * @version v1.0
 * @since 2020/3/23 17:34
 */
@Slf4j
@UtilityClass
public class ArrayUtils extends ArrayUtil {

    /**
     * isEmpty
     *
     * <p>
     *  数组是否为空<br>
     *  此方法会匹配单一对象，如果此对象为{@code null}则返回true<br>
     *  如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
     *  如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     * </p>
     *
     * @since 2020/3/23 17:53
     * @param array 数组
     * @return boolean
     */
    public static boolean isEmpty(Object array) {
        if (null == array) {
            return true;
        } else if (isArray(array)) {
            return 0 == Array.getLength(array);
        }
        throw new UtilException("Object to provide is not a Array !");
    }


    /**
     * 数组或集合转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (ArrayUtils.isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception e) {
                log.error("cn.fxbin.framework.core.util.ArrayUtils.toStringg error", e);
                return Arrays.toString(wrap(obj));
            }
        }
        return obj.toString();
    }

}
