package top.rainrem.boot.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import top.rainrem.boot.core.validator.FieldValidator;

import java.lang.annotation.*;

/**
 * 用于验证字段值是否合法的注解
 *
 * @author LightRain
 * @since 2025年7月29日18:10:10
 */
@Documented
@Constraint(validatedBy = FieldValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidField {

    /**
     * 验证失败时的错误信息。
     */
    String message() default "非法字段";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 允许的合法值列表。
     */
    String[] allowedValues();

}
