package top.rainrem.boot.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import top.rainrem.boot.system.model.entity.Dict;
import top.rainrem.boot.system.model.form.DictForm;
import top.rainrem.boot.system.model.vo.DictPageVO;

/**
 * 字典对象转换器
 *
 * @author LightRain
 * @since 2025年7月26日19:40:59
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> toPageVo(Page<Dict> page);

    DictForm toForm(Dict entity);

    Dict toEntity(DictForm entity);
}
