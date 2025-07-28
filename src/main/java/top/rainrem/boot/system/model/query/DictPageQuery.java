package top.rainrem.boot.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rainrem.boot.common.base.BasePageQuery;

/**
 * 字典分页查询对象
 *
 * @author LightRain
 * @since 2025年7月26日19:33:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典分页查询对象")
public class DictPageQuery extends BasePageQuery {

    @Schema(description = "关键字(字典名称)")
    private String keywords;

}
