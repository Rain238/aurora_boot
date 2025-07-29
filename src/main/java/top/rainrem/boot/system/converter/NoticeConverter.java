package top.rainrem.boot.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.rainrem.boot.system.model.bo.NoticeBO;
import top.rainrem.boot.system.model.entity.Notice;
import top.rainrem.boot.system.model.form.NoticeForm;
import top.rainrem.boot.system.model.vo.NoticeDetailVO;
import top.rainrem.boot.system.model.vo.NoticePageVO;

/**
 * 通知公告对象转换器
 *
 * @author LightRain
 * @since 2025年7月27日13:14:13
 */
@Mapper(componentModel = "spring")
public interface NoticeConverter{


    @Mappings({
            @Mapping(target = "targetUserIds", expression = "java(cn.hutool.core.util.StrUtil.split(entity.getTargetUserIds(),\",\"))")
    })
    NoticeForm toForm(Notice entity);

    @Mappings({
            @Mapping(target = "targetUserIds", expression = "java(cn.hutool.core.collection.CollUtil.join(formData.getTargetUserIds(),\",\"))")
    })
    Notice toEntity(NoticeForm formData);

    NoticePageVO toPageVo(NoticeBO bo);

    Page<NoticePageVO> toPageVo(Page<NoticeBO> noticePage);

    NoticeDetailVO toDetailVO(NoticeBO noticeBO);
}
