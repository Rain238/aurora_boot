package top.rainrem.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rainrem.boot.common.base.BaseEntity;

/**
 * 字典实体
 *
 * @author LightRain
 * @since 2025年7月26日19:35:38
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = false)
public class Dict extends BaseEntity {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String name;


    /**
     * 状态（1：启用, 0：停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}