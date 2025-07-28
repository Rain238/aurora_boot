package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * 菜单Mapper
 *
 * @author LightRain
 * @since 2025年7月26日19:20:03
 */

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取菜单路由列表
     *
     * @param roleCodes 角色编码集合
     */
    List<Menu> getMenusByRoleCodes(Set<String> roleCodes);

}