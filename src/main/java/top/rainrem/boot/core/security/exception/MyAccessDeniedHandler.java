package top.rainrem.boot.core.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.common.util.ResponseUtils;

import java.io.IOException;

/**
 * 无权限访问处理器
 *
 * @author LightRain
 * @since 2025/7/21
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 处理无权限请求
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 拒绝访问处理程序
     * @throws IOException      异常
     * @throws ServletException 请求异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 返回未授权的状态
        ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_UNAUTHORIZED);
    }
}
