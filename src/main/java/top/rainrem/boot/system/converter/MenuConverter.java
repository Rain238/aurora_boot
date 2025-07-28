package top.rainrem.boot.system.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.rainrem.boot.system.model.entity.Menu;
import top.rainrem.boot.system.model.form.MenuForm;
import top.rainrem.boot.system.model.vo.MenuVO;

/**
 * 菜单对象转换器
 *
 * @author LightRain
 * @since 2025年7月26日19:17:47
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO toVo(Menu entity);

    @Mapping(target = "params", ignore = true)
    MenuForm toForm(Menu entity);

    @Mapping(target = "params", ignore = true)
    Menu toEntity(MenuForm menuForm);

}