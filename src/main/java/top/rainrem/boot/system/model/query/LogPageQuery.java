package top.rainrem.boot.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.rainrem.boot.common.base.BasePageQuery;

import java.util.List;

/**
 * 日志分页查询对象
 *
 * @author LightRain
 * @since 2025年7月27日13:46:50
 */
@Getter
@Setter
@Schema(description = "日志分页查询对象")
public class LogPageQuery extends BasePageQuery {

    @Schema(description = "关键字(日志内容/请求路径/请求方法/地区/浏览器/终端系统)")
    private String keywords;

    @Schema(description = "操作时间范围")
    List<String> createTime;

}
