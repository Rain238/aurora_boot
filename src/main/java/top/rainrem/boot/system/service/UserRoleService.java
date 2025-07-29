package top.rainrem.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.rainrem.boot.system.model.entity.UserRole;

import java.util.List;

/**
 * 用户角色接口
 *
 * @author LightRain
 * @since 2025年7月23日16:49:37
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存用户角色
     *
     * @param userId  用户ID
     * @param roleIds 选择的角色ID集合
     */
    void saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 判断角色是否存在绑定的用户
     *
     * @param roleId 角色ID
     * @return true=已分配 false=未分配
     */
    boolean hasAssignedUsers(Long roleId);

}
