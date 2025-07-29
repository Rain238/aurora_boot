package top.rainrem.boot.system.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import top.rainrem.boot.common.base.IBaseEnum;

/**
 * 通知目标类型枚举
 *
 * @author LightRain
 * @since 2025年7月27日13:34:04
 */
@Getter
@Schema(enumAsRef = true)
public enum NoticeTargetEnum implements IBaseEnum<Integer> {

    ALL(1, "全体"),
    SPECIFIED(2, "指定");


    private final Integer value;

    private final String label;

    NoticeTargetEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
