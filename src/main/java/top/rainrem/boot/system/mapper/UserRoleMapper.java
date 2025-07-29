package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.UserRole;

/**
 * 用户角色访问层
 *
 * @author LightRain
 * @since 2025年7月23日16:09:08
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取角色绑定的用户数
     *
     * @param roleId 角色id
     * @return int
     */
    int countUsersForRole(Long roleId);

}
