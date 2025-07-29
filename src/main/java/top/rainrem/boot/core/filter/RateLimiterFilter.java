package top.rainrem.boot.core.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.common.constant.SystemConstants;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.common.util.IPUtils;
import top.rainrem.boot.common.util.ResponseUtils;
import top.rainrem.boot.system.service.ConfigService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * IP限流过滤器
 *
 * @author LightRain
 * @since 2025/7/22
 */
@Slf4j
public class RateLimiterFilter extends OncePerRequestFilter {

    // Redis模版
    private final RedisTemplate<String, Object> redisTemplate;

    // 系统配置Service
    private final ConfigService configService;

    // 默认 IP 限流阈值
    private static final long DEFAULT_IP_LIMIT = 10L;


    public RateLimiterFilter(RedisTemplate<String, Object> redisTemplate, ConfigService configService) {
        this.redisTemplate = redisTemplate;
        this.configService = configService;
    }

    /**
     * 执行 IP 限流逻辑
     * 如果 IP 请求超出限制，直接返回限流响应，否则继续执行过滤链
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求的 IP 地址
        String ip = IPUtils.getIpAddr(request);
        // 判断是否限流
        if (rateLimit(ip)) {
            // 返回限流错误消息
            ResponseUtils.writeErrMsg(response, ResultCode.REQUEST_CONCURRENCY_LIMIT_EXCEEDED);
            return;
        }
        // 未触发限流，继续执行过滤器
        filterChain.doFilter(request, response);
    }

    /**
     * 判断IP是否触发限流机制
     * 默认限制同一 IP 每秒最多请求 10 次,可通过系统配置调整
     * 如果系统未配置限流阈值,默认则跳过限流
     *
     * @param ip IP地址
     * @return 是否限流：true 表示限流；false 表示未限流
     */
    private boolean rateLimit(String ip) {
        // 限流 Redis 键
        String key = StrUtil.format(RedisConstants.RateLimiter.IP, ip);
        // 自增请求计数
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == null || count == 1) {
            // 第一次访问时设置过期时间为 1 秒
            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }

        // 获取系统配置的限流阈值
        Object systemConfig = configService.getSystemConfig(SystemConstants.SYSTEM_CONFIG_IP_QPS_LIMIT_KEY);
        if (systemConfig == null) {
            // 系统未配置限流，跳过限流逻辑
            log.warn("系统未配置限流阈值，跳过限流");
            return false;
        }

        // 转换系统配置为限流值，默认为 10
        long limit = Convert.toLong(systemConfig, DEFAULT_IP_LIMIT);
        return count != null && count > limit;
    }
}
