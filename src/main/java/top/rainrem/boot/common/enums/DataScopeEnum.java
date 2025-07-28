package top.rainrem.boot.common.enums;

import lombok.Getter;
import top.rainrem.boot.common.base.IBaseEnum;

/**
 * 数据权限枚举
 *
 * @author LightRain
 * @since 2025年7月23日20:03:43
 */
@Getter
public enum DataScopeEnum implements IBaseEnum<Integer> {

    /**
     * value 越小，数据权限范围越大
     */
    ALL(1, "所有数据"),
    SELF(4, "本人数据");

    private final Integer value;

    private final String label;

    DataScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
