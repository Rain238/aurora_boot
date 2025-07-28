package top.rainrem.boot.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import top.rainrem.boot.system.model.entity.Config;
import top.rainrem.boot.system.model.form.ConfigForm;
import top.rainrem.boot.system.model.vo.ConfigVO;

/**
 * 系统配置对象转换器
 * @author LightRain
 * @since 2025/7/22
 */
@Mapper(componentModel = "spring")
public interface ConfigConverter {
    Page<ConfigVO> toPageVo(Page<Config> page);

    Config toEntity(ConfigForm configForm);

    ConfigForm toForm(Config entity);
}
