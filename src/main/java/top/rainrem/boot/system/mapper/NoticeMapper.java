package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.rainrem.boot.system.model.bo.NoticeBO;
import top.rainrem.boot.system.model.entity.Notice;
import top.rainrem.boot.system.model.query.NoticePageQuery;
import top.rainrem.boot.system.model.vo.NoticePageVO;

/**
 * 通知公告Mapper接口
 *
 * @author LightRain
 * @since 2025年7月27日13:19:42
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取通知公告分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return 通知公告分页数据
     */
    Page<NoticeBO> getNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);

    /**
     * 获取阅读时通知公告详情
     *
     * @param id 通知公告ID
     * @return 通知公告详情
     */
    NoticeBO getNoticeDetail(@Param("id") Long id);
}
