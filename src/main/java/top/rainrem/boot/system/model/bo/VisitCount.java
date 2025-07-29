package top.rainrem.boot.system.model.bo;

import lombok.Data;

/**
 * 特定日期访问统计
 *
 * @author LightRain
 * @since 2025年7月27日13:50:36
 */
@Data
public class VisitCount {

    /**
     * 日期 yyyy-MM-dd
     */
    private String date;

    /**
     * 访问次数
     */
    private Integer count;
}
