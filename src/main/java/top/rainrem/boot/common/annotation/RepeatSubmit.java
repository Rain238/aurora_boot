package top.rainrem.boot.common.annotation;

import java.lang.annotation.*;

/**
 * 防止重复提交注解
 * <p>
 * 该注解用于方法上，防止在指定时间内的重复提交。 默认时间为5秒。
 *
 * @author LightRain
 * @since 2025年7月25日20:56:54
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {

    /**
     * 锁过期时间（秒）
     * <p>
     * 默认5秒内不允许重复提交
     */
    int expire() default 5;

}
