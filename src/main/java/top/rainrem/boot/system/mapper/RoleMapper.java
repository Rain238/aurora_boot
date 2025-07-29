package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.Role;

import java.util.Set;

/**
 * 角色持久层接口
 *
 * @author LightRain
 * @since 2025年7月23日16:41:06
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获取最大范围的数据权限
     *
     * @param roles 角色编码集合
     * @return Integer
     */

    Integer getMaximumDataScope(Set<String> roles);
}
