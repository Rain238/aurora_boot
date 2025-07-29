package top.rainrem.boot.core.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.common.util.ResponseUtils;

import java.io.IOException;

/**
 * 统一处理 SpringSecurity 认证失败异常
 *
 * @author LightRain
 * @since 2025/7/21
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 认证失败处理入口方法
     *
     * @param request       触发异常的请求对象（可用于获取请求头、参数等）
     * @param response      响应对象（用于写入错误信息）
     * @param authException 认证异常对象（包含具体失败原因）
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResponseUtils.writeErrMsg(response, ResultCode.USER_PASSWORD_ERROR);
        } else if (authException instanceof InsufficientAuthenticationException) {
            // 请求头缺失Authorization、Token格式错误、Token过期、签名验证失败
            ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_TOKEN_INVALID);
        } else {
            // 其他未明确处理的认证异常（如账户被锁定、账户禁用等）
            ResponseUtils.writeErrMsg(response, ResultCode.USER_LOGIN_EXCEPTION, authException.getMessage());
        }
    }
}
