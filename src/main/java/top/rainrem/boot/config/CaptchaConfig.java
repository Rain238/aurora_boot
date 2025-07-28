package top.rainrem.boot.config;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.rainrem.boot.config.property.CaptchaProperties;

import java.awt.*;

/**
 * 验证码配置
 *
 * @author LightRain
 * @since 2025年7月22日17:28:11
 */
@Configuration
public class CaptchaConfig {

    /**
     * 验证码属性
     */
    @Resource
    private CaptchaProperties captchaProperties;

    /**
     * 验证码文字生成器
     *
     * @return CodeGenerator
     */
    @Bean
    public CodeGenerator codeGenerator() {
        String codeType = captchaProperties.getCode().getType();
        int codeLength = captchaProperties.getCode().getLength();
        if ("math".equalsIgnoreCase(codeType)) {
            return new MathGenerator(codeLength);
        } else if ("random".equalsIgnoreCase(codeType)) {
            return new RandomGenerator(codeLength);
        } else {
            throw new IllegalArgumentException("Invalid captcha codegen type: " + codeType);
        }
    }

    /**
     * 验证码字体
     */
    @Bean
    public Font captchaFont() {
        String fontName = captchaProperties.getFont().getName();
        int fontSize = captchaProperties.getFont().getSize();
        int fontWight = captchaProperties.getFont().getWeight();
        return new Font(fontName, fontWight, fontSize);
    }

}
