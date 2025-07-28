package top.rainrem.boot.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改邮箱表单
 *
 * @author LightRain
 * @since 2025年7月22日19:22:40
 */
@Data
@Schema(description = "修改邮箱表单")
public class EmailUpdateForm {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
