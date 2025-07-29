package top.rainrem.boot.core.security.filter;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import top.rainrem.boot.common.constant.SecurityConstants;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.common.util.ResponseUtils;
import top.rainrem.boot.core.security.token.TokenManager;

import java.io.IOException;

/**
 * Token 认证校验过滤器
 *
 * @author LightRain
 * @since 2025年7月22日17:33:56
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Token 管理器
     */
    private final TokenManager tokenManager;

    public TokenAuthenticationFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 校验 Token 包括验签和是否过期
     * 如果 Token 有效,将 Token 解析为 Authentication对象,并设置到 Spring Security 上下文中
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取客户端请求中携带的 Authorization 头
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StrUtil.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
                // 剥离 Bearer 前缀获取原始令牌
                String rawToken = authorizationHeader.substring(SecurityConstants.BEARER_TOKEN_PREFIX.length());
                // 执行令牌有效性检查，包含密码学验证和过期时间验证
                boolean isValidToken = tokenManager.validateToken(rawToken);
                if (!isValidToken) {
                    ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_TOKEN_INVALID);
                    return;
                }

                // 将令牌解析为 Spring Security 上下文认证对象
                Authentication authentication = tokenManager.parseToken(rawToken);
                // 设置到上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // 安全上下文清除保障（防止上下文残留）
            SecurityContextHolder.clearContext();
            ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_TOKEN_INVALID);
            return;
        }
        // 继续后续过滤器链执行
        filterChain.doFilter(request, response);
    }
}
