package top.rainrem.boot.system.model.form;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户导入对象
 *
 * @author LightRain
 * @since 2025年7月23日19:23:09
 */
@Data
public class UserImportDTO {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "用户昵称")
    private String nickname;

    @ExcelProperty(value = "性别")
    private String genderLabel;

    @ExcelProperty(value = "手机号码")
    private String mobile;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty("角色")
    private String roleCodes;

}
