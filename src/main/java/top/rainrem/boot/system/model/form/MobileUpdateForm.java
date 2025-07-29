package top.rainrem.boot.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改手机表单
 *
 * @author LightRain
 * @since 2025年7月22日19:21:44
 */
@Data
@Schema(description = "修改手机表单")
public class MobileUpdateForm {

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
