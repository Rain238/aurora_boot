package top.rainrem.boot.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典分页VO（值对象）
 *
 * @author LightRain
 * @since 2025年7月23日19:18:46
 */
@Getter
@Setter
@Schema(description = "字典分页对象")
public class DictPageVO {

    @Schema(description = "字典ID")
    private Long id;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典状态（1-启用，0-禁用）")
    private Integer status;

}
