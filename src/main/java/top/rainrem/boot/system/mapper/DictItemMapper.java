package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.DictItem;
import top.rainrem.boot.system.model.query.DictItemPageQuery;
import top.rainrem.boot.system.model.vo.DictItemPageVO;

/**
 * 字典项映射层
 *
 * @author LightRain
 * @since 2025年7月23日19:17:27
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

    /**
     * 字典项分页列表
     */
    Page<DictItemPageVO> getDictItemPage(Page<DictItemPageVO> page, DictItemPageQuery queryParams);
}

