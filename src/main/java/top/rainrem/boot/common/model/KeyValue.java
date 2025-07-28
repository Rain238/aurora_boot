package top.rainrem.boot.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 键值对
 *
 * @author LightRain
 * @since 2025年7月26日18:56:31
 */
@Data
@NoArgsConstructor
@Schema(description = "键值对")
public class KeyValue {

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Schema(description = "选项的值")
    private String key;

    @Schema(description = "选项的标签")
    private String value;

}
