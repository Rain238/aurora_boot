package top.rainrem.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.rainrem.boot.system.model.entity.UserNotice;
import top.rainrem.boot.system.model.query.NoticePageQuery;
import top.rainrem.boot.system.model.vo.NoticePageVO;
import top.rainrem.boot.system.model.vo.UserNoticePageVO;

/**
 * 用户公告状态服务类
 *
 * @author LightRain
 * @since 2025年7月27日13:17:15
 */
public interface UserNoticeService extends IService<UserNotice> {

    /**
     * 全部标记为已读
     *
     * @return 是否成功
     */
    boolean readAll();

    /**
     * 分页获取我的通知公告
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return 我的通知公告分页列表
     */
    IPage<UserNoticePageVO> getMyNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);
}
