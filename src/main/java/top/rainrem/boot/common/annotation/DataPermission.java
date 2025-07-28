package top.rainrem.boot.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author LightRain
 * @since 2025年7月22日19:30:04
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataPermission {

    /**
     * 数据权限 {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     */
    String deptAlias() default "";

    String deptIdColumnName() default "dept_id";

    String userAlias() default "";

    String userIdColumnName() default "create_by";

}
