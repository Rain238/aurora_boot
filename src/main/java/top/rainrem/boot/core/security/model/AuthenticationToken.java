package top.rainrem.boot.core.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 认证令牌响应对象
 *
 * @author LightRain
 * @since 2025年7月22日17:37:10
 */
@Data
@Builder
@Schema(description = "认证令牌响应对象")
public class AuthenticationToken {

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType;

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "过期时间(单位：秒)")
    private Integer expiresIn;

}
