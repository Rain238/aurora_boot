package top.rainrem.boot.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信小程序Code登录请求参数
 *
 * @author LightRain
 * @since 2025年7月25日18:30:41
 */
@Data
@Schema(description = "微信小程序Code登录请求参数")
public class WxMiniAppCodeLoginDTO {

    @Schema(description = "微信小程序登录是获取的code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "code不能为空")
    private String code;

}
