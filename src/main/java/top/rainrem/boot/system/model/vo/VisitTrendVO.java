package top.rainrem.boot.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 访问趋势VO
 *
 * @author LightRain
 * @since 2025年7月27日13:47:23
 */
@Getter
@Setter
@Schema(description = "访问趋势VO")
public class VisitTrendVO {

    @Schema(description = "日期列表")
    private List<String> dates;

    @Schema(description = "浏览量(PV)")
    private List<Integer> pvList;

    @Schema(description = "IP数")
    private List<Integer> ipList;

}
