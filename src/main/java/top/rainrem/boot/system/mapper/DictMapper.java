package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.Dict;
import top.rainrem.boot.system.model.query.DictPageQuery;
import top.rainrem.boot.system.model.vo.DictPageVO;

/**
 * 字典Mapper
 *
 * @author LightRain
 * @since 2025年7月26日19:35:08
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 字典分页列表
     *
     * @param page 分页参数
     * @param queryParams 查询参数
     * @return 字典分页列表
     */
    Page<DictPageVO> getDictPage(Page<DictPageVO> page, DictPageQuery queryParams);

}



