package top.rainrem.boot.system.enums;

import lombok.Getter;
import top.rainrem.boot.common.base.IBaseEnum;

/**
 * 字典编码枚举
 *
 * @author LightRain
 * @since 2025年7月23日19:27:54
 */
@Getter
public enum DictCodeEnum implements IBaseEnum<String> {

    GENDER("gender", "性别"),
    NOTICE_TYPE("notice_type", "通知类型"),
    NOTICE_LEVEL("notice_level", "通知级别");

    private final String value;

    private final String label;

    DictCodeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
