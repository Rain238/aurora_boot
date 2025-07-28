package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.rainrem.boot.system.model.entity.Config;

/**
 * 系统配置访问层
 * @author LightRain
 * @since 2025/7/22
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
}
