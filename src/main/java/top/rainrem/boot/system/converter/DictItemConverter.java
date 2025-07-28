package top.rainrem.boot.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import top.rainrem.boot.common.model.Option;
import top.rainrem.boot.system.model.entity.DictItem;
import top.rainrem.boot.system.model.form.DictItemForm;
import top.rainrem.boot.system.model.vo.DictPageVO;

import java.util.List;

/**
 * 字典项对象转换器
 *
 * @author LightRain
 * @since 2025年7月23日19:18:14
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter {

    Page<DictPageVO> toPageVo(Page<DictItem> page);

    DictItemForm toForm(DictItem entity);

    DictItem toEntity(DictItemForm formFata);

    Option<Long> toOption(DictItem dictItem);
    List<Option<Long>> toOption(List<DictItem> dictData);
}
