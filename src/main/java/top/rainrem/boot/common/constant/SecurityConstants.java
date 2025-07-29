package top.rainrem.boot.common.constant;

/**
 * 安全模块常量
 *
 * @author LightRain
 * @since 2025年7月22日17:08:22
 */
public interface SecurityConstants {
    /**
     * 登录路径
     */
    String LOGIN_PATH = "/api/v1/auth/login";

    /**
     * JWT Token 前缀
     */
    String BEARER_TOKEN_PREFIX = "Bearer ";

    /**
     * 角色前缀，用于区分 authorities 角色和权限， ROLE_* 角色 、没有前缀的是权限
     */
    String ROLE_PREFIX = "ROLE_";
}
