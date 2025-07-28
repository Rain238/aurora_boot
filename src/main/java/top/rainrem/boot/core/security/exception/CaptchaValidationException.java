package top.rainrem.boot.core.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验异常处理
 *
 * @author LightRain
 * @since 2025年7月25日20:31:24
 */
public class CaptchaValidationException extends AuthenticationException {
    public CaptchaValidationException(String msg) {
        super(msg);
    }
}