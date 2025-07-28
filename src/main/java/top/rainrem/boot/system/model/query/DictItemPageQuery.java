package top.rainrem.boot.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rainrem.boot.common.base.BasePageQuery;

/**
 * 字典项分页查询对象
 * @author LightRain
 * @since 2025年7月23日19:13:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典项分页查询对象")
public class DictItemPageQuery extends BasePageQuery {
    @Schema(description = "关键字(字典项值/字典项名称)")
    private String keywords;

    @Schema(description = "字典编码")
    private String dictCode;
}
