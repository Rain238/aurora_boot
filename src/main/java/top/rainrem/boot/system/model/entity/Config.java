package top.rainrem.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rainrem.boot.common.base.BaseEntity;

/**
 * 系统配置对象
 * @author LightRain
 * @since 2025/7/22
 */
// 自动生成 getter/setter/toString/hashCode/equals
@Data
// 控制是否包含父类字段
@EqualsAndHashCode(callSuper = false)
// 接口文档模型说明
@Schema(description = "系统配置")
// 指定数据库表名
@TableName("sys_config")
public class Config extends BaseEntity {

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 描述、备注
     */
    private String remark;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;
}
