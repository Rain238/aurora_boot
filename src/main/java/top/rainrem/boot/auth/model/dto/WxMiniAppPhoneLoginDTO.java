package top.rainrem.boot.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信小程序手机号登录请求参数
 *
 * @author LightRain
 * @since 2025年7月25日18:34:07
 */
@Data
@Schema(description = "微信小程序手机号登录请求参数")
public class WxMiniAppPhoneLoginDTO {

    @NotBlank(message = "code不能为空")
    @Schema(description = "小程序登录是获取的code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "包括敏感数据在内的完整用户信息的加密数据")
    private String encryptedData;

    @Schema(description = "加密算法的初始向量")
    private String iv;

}
