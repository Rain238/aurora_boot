package top.rainrem.boot.system.model.bo;

import lombok.Data;

import java.util.Set;

/**
 * 角色权限业务对象
 *
 * @author LightRain
 * @since 2025年7月23日18:29:48
 */
@Data
public class RolePermsBO {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限标识集合
     */
    private Set<String> perms;

}
