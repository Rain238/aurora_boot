package top.rainrem.boot.common.enums;

import lombok.Getter;
import top.rainrem.boot.common.base.IBaseEnum;

/**
 * 状态枚举
 *
 * @author LightRain
 * @since 2025年7月25日21:29:14
 */
@Getter
public enum StatusEnum implements IBaseEnum<Integer> {

    ENABLE(1, "启用"),
    DISABLE (0, "禁用");

    private final Integer value;

    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
