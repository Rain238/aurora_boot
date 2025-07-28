package top.rainrem.boot.common.constant;

/**
 * JWT Claims常量声明
 * <p>
 * JWT Claims 属于 Payload 的一部分，包含了一些实体（通常指的用户）的状态和额外的元数据。
 *
 * @author LightRain
 * @since 2025年7月22日18:11:21
 */
public interface JwtClaimConstants {
    /**
     * 令牌类型
     */
    String TOKEN_TYPE = "tokenType";

    /**
     * 用户ID
     */
    String USER_ID = "userId";

    /**
     * 数据权限
     */
    String DATA_SCOPE = "dataScope";

    /**
     * 权限(角色Code)集合
     */
    String AUTHORITIES = "authorities";
}
