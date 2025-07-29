package top.rainrem.boot.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.rainrem.boot.common.model.Option;
import top.rainrem.boot.system.model.entity.Role;
import top.rainrem.boot.system.model.form.RoleForm;
import top.rainrem.boot.system.model.vo.RolePageVO;

import java.util.List;

/**
 * 角色对象转换器
 *
 * @author LightRain
 * @since 2025年7月23日18:38:57
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    Page<RolePageVO> toPageVo(Page<Role> page);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    Option<Long> toOption(Role role);

    List<Option<Long>> toOptions(List<Role> roles);

    Role toEntity(RoleForm roleForm);

    RoleForm toForm(Role entity);
}