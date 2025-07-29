package top.rainrem.boot.shared.sms.enums;

import lombok.Getter;
import top.rainrem.boot.common.base.IBaseEnum;

/**
 * 短信类型枚举
 * <p>
 * value 值对应 application-*.yml 中的 sms.templates.* 配置
 *
 * @author LightRain
 * @since 2025年7月29日18:07:59
 */
@Getter
public enum SmsTypeEnum implements IBaseEnum<String> {

    /**
     * 注册短信验证码
     */
    REGISTER("register", "注册短信验证码"),

    /**
     * 登录短信验证码
     */
    LOGIN("login", "登录短信验证码"),

    /**
     * 修改手机号短信验证码
     */
    CHANGE_MOBILE("change-mobile", "修改手机号短信验证码");

    private final String value;
    private final String label;

    SmsTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
