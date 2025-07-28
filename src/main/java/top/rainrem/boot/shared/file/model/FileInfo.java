package top.rainrem.boot.shared.file.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文件对象
 *
 * @author LightRain
 * @since 2025年7月26日21:08:17
 */
@Data
@Schema(description = "文件对象")
public class FileInfo {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件URL")
    private String url;

}