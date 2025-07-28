package top.rainrem.boot.core.filter;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.common.constant.SecurityConstants;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.common.util.ResponseUtils;

import java.io.IOException;

/**
 * 验证码效验过滤器
 *
 * @author LightRain
 * @since 2025年7月22日16:50:07
 */
public class CaptchaValidationFilter extends OncePerRequestFilter {

    // 验证码静态常量
    public static final String CAPTCHA_CODE_PARAM_NAME = "captchaCode";
    public static final String CAPTCHA_KEY_PARAM_NAME = "captchaKey";

    private final RedisTemplate<String, Object> redisTemplate;

    private final CodeGenerator codeGenerator;

    // 创建模式解析器
    PathPatternParser patternParser = new PathPatternParser();
    // 解析路径
    PathPattern pattern = patternParser.parse(SecurityConstants.LOGIN_PATH);

    // 创建显示请求匹配器
    private final RequestMatcher login_path_request_matcher = request -> {
        String path = request.getServletPath();
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return false;
        }
        return pattern.matches(PathContainer.parsePath(path));
    };

    public CaptchaValidationFilter(RedisTemplate<String, Object> redisTemplate, CodeGenerator codeGenerator) {
        this.redisTemplate = redisTemplate;
        this.codeGenerator = codeGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 校验登录接口的验证码
        if (login_path_request_matcher.matches(request)){
            // 请求中的验证码
            String captchaCode = request.getParameter(CAPTCHA_CODE_PARAM_NAME);
            // 不需要验证码则开启这个判断
//            if (StrUtil.isBlank(captchaCode)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
            // 缓存中的验证码
            String verifyCodeKey = request.getParameter(CAPTCHA_KEY_PARAM_NAME);
            String cacheVerifyCode = (String) redisTemplate.opsForValue().get(StrUtil.format(RedisConstants.Captcha.IMAGE_CODE, verifyCodeKey));

            // 验证码比对
            if (codeGenerator.verify(cacheVerifyCode, captchaCode)) {
                filterChain.doFilter(request, response);
            } else {
                ResponseUtils.writeErrMsg(response, ResultCode.USER_VERIFICATION_CODE_ERROR);
            }
        }else {
            // 非登录接口放行
            filterChain.doFilter(request, response);
        }
    }

}
