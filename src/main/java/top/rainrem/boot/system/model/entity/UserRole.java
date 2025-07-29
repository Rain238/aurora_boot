package top.rainrem.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联表
 *
 * @author LightRain
 * @since 2025年7月23日15:51:59
 */
@Data
@TableName("sys_user_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
