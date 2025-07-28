package top.rainrem.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rainrem.boot.common.base.BaseEntity;
/**
 * 字典项实体对象
 *
 * @author LightRain
 * @since 2025年7月23日19:11:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_item")
public class DictItem extends BaseEntity {
    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典项名称
     */
    private String label;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（1-正常，0-禁用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 标签类型
     */
    private String tagType;
}
