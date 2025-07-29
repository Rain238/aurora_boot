package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.bo.RolePermsBO;
import top.rainrem.boot.system.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单访问层
 *
 * @author LightRain
 * @since 2025年7月23日18:29:12
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     */
    List<RolePermsBO> getRolePermsList(String roleCode,Integer enumValue);


    /**
     * 获取角色权限集合
     *
     * @param roles
     * @return 权限列表
     */
    Set<String> listRolePerms(Set<String> roles);
}
