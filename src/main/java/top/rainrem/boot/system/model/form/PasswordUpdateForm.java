package top.rainrem.boot.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改密码表单
 *
 * @author LightRain
 * @since 2025年7月22日19:20:35
 */
@Data
@Schema(description = "修改密码表单")
public class PasswordUpdateForm {

    @Schema(description = "原密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "确认密码")
    private String confirmPassword;
}
