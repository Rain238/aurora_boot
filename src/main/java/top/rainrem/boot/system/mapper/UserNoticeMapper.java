package top.rainrem.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.rainrem.boot.system.model.entity.UserNotice;
import top.rainrem.boot.system.model.query.NoticePageQuery;
import top.rainrem.boot.system.model.vo.NoticePageVO;
import top.rainrem.boot.system.model.vo.UserNoticePageVO;

/**
 * 用户公告状态Mapper接口
 *
 * @author LightRain
 * @since 2025年7月27日13:40:35
 */
@Mapper
public interface UserNoticeMapper extends BaseMapper<UserNotice> {
    /**
     * 分页获取我的通知公告
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return 通知公告分页列表
     */
    IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page, @Param("queryParams") NoticePageQuery queryParams);
}
