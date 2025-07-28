package top.rainrem.boot.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.rainrem.boot.common.base.BasePageQuery;

/**
 * 系统配置分页查询
 * @author LightRain
 * @since 2025/7/22
 */
@Getter
@Setter
@Schema(description = "系统配置分页查询")
public class ConfigPageQuery extends BasePageQuery {
    @Schema(description="关键字(配置项名称/配置项值)")
    private String keywords;
}
