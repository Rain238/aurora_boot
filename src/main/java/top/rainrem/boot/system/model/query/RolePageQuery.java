package top.rainrem.boot.system.model.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.rainrem.boot.common.base.BasePageQuery;

import java.time.LocalDateTime;

/**
 * 角色分页查询对象
 *
 * @author LightRain
 * @since 2025年7月23日16:37:36
 */
@Getter
@Setter
@Schema(description = "角色分页查询对象")
public class RolePageQuery extends BasePageQuery {

    @Schema(description="关键字(角色名称/角色编码)")
    private String keywords;

    @Schema(description="开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @Schema(description="结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
