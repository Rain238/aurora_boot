package top.rainrem.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.rainrem.boot.system.model.entity.Log;
import top.rainrem.boot.system.model.query.LogPageQuery;
import top.rainrem.boot.system.model.vo.LogPageVO;
import top.rainrem.boot.system.model.vo.VisitStatsVO;
import top.rainrem.boot.system.model.vo.VisitTrendVO;

import java.time.LocalDate;

/**
 * 系统日志服务接口
 * @author LightRain
 * @since 2025年7月27日13:43:32
 */
public interface LogService extends IService<Log> {
    /**
     * 获取日志分页列表
     */
    Page<LogPageVO> getLogPage(LogPageQuery queryParams);


    /**
     * 获取访问趋势
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    VisitTrendVO getVisitTrend(LocalDate startDate, LocalDate endDate);

    /**
     * 获取访问统计
     */
    VisitStatsVO getVisitStats();
}
