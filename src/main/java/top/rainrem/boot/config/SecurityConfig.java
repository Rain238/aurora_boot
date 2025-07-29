package top.rainrem.boot.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.rainrem.boot.config.property.SecurityProperties;
import top.rainrem.boot.core.filter.CaptchaValidationFilter;
import top.rainrem.boot.core.filter.RateLimiterFilter;
import top.rainrem.boot.core.security.exception.MyAccessDeniedHandler;
import top.rainrem.boot.core.security.exception.MyAuthenticationEntryPoint;
import top.rainrem.boot.core.security.extension.sms.SmsAuthenticationProvider;
import top.rainrem.boot.core.security.extension.wx.WxMiniAppCodeAuthenticationProvider;
import top.rainrem.boot.core.security.extension.wx.WxMiniAppPhoneAuthenticationProvider;
import top.rainrem.boot.core.security.filter.TokenAuthenticationFilter;
import top.rainrem.boot.core.security.service.SysUserDetailsService;
import top.rainrem.boot.core.security.token.TokenManager;
import top.rainrem.boot.system.service.ConfigService;
import top.rainrem.boot.system.service.UserService;

/**
 * Spring Security配置
 *
 * @author LightRain
 * @since 2025/7/21
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * redis模版
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 加载配置
     */
    private final SecurityProperties securityProperties;

    /**
     * 系统配置Service
     */
    private final ConfigService configService;

    /**
     * 验证码生成
     */
    private final CodeGenerator codeGenerator;

    /**
     * Token 管理器
     */
    private final TokenManager tokenManager;

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 系统用户认证
     */
    private final SysUserDetailsService userDetailsService;

    /**
     * 微信服务
     */
    private final WxMaService wxMaService;


    /**
     * 配置安全过滤链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 先处理授权请求路径
        return httpSecurity.authorizeHttpRequests(requestMatcherRegistry -> {
                    // 配置无需登录即可访问的公共接口
                    String[] ignoreUrls = securityProperties.getIgnoreUrls();
                    if (ArrayUtil.isNotEmpty(ignoreUrls)) {
                        requestMatcherRegistry.requestMatchers(ignoreUrls).permitAll();
                    }
                    // 其它所有请求则需要登录后才可以访问
                    requestMatcherRegistry.anyRequest().authenticated();
                })
                // 异常处理
                .exceptionHandling(configurer ->
                        configurer
                                // 身份验证入口点
                                .authenticationEntryPoint(new MyAuthenticationEntryPoint()) // 未认证异常处理器
                                // 拒绝访问处理程序
                                .accessDeniedHandler(new MyAccessDeniedHandler()) // 无权限访问异常处理器
                )
                // 禁用默认的Spring Security特性适用于前后端分离架构
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态认证，不使用 Session
                )
                // 禁用CSRF防护，前后端分离无序此防护机制
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认的表单登录功能，前后端分离采用Token认证方式
                .formLogin(AbstractHttpConfigurer::disable)
                //禁用HttpBasic认证，避免弹窗式认证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用 X-Frame-Options 响应头，允许页面被嵌套到 iframe 中
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 限流过滤器
                .addFilterBefore(new RateLimiterFilter(redisTemplate, configService), UsernamePasswordAuthenticationFilter.class)
                // 验证码校验过滤器
                .addFilterBefore(new CaptchaValidationFilter(redisTemplate, codeGenerator), UsernamePasswordAuthenticationFilter.class)
                // 验证和解析过滤器
                .addFilterBefore(new TokenAuthenticationFilter(tokenManager), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * 配置Web安全自定义器，以忽略特定请求路径的安全性检查。
     * <p>
     * 该配置用于指定哪些请求路径不经过Spring Security过滤器链。通常用于静态资源文件。
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            // 从配置类中获取无需认证的 URL 列表
            String[] unsecuredUrls = securityProperties.getUnsecuredUrls();
            if (ArrayUtil.isNotEmpty(unsecuredUrls)) {
                // 如果配置了无需认证的 URL，则将这些 URL 加入 Spring Security 忽略列表
                web.ignoring().requestMatchers(unsecuredUrls);
            }
        };
    }

    /**
     * 默认密码认证的 Provider
     * 创建并配置 DaoAuthenticationProvider，用于基于数据库的身份认证
     *
     * @return 配置好的 DaoAuthenticationProvider 实例
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        // 实例化 DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // 设置密码编码器，确保密码校验时采用正确的加密算法
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        // 设置用户信息加载服务，从数据库或其他存储获取用户详情
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 返回配置好的身份认证提供器
        return daoAuthenticationProvider;
    }

    /**
     * 微信小程序Code认证Provider
     */
    @Bean
    public WxMiniAppCodeAuthenticationProvider wxMiniAppCodeAuthenticationProvider() {
        return new WxMiniAppCodeAuthenticationProvider(userService, wxMaService);
    }

    /**
     * 微信小程序手机号认证Provider
     */
    @Bean
    public WxMiniAppPhoneAuthenticationProvider wxMiniAppPhoneAuthenticationProvider() {
        return new WxMiniAppPhoneAuthenticationProvider(userService, wxMaService);
    }

    /**
     * 短信验证码认证 Provider
     */
    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        return new SmsAuthenticationProvider(userService, redisTemplate);
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider daoAuthenticationProvider,
            WxMiniAppCodeAuthenticationProvider wxMiniAppCodeAuthenticationProvider,
            WxMiniAppPhoneAuthenticationProvider wxMiniAppPhoneAuthenticationProvider,
            SmsAuthenticationProvider smsAuthenticationProvider
    ) {
        return new ProviderManager(
                daoAuthenticationProvider,
                wxMiniAppCodeAuthenticationProvider,
                wxMiniAppPhoneAuthenticationProvider,
                smsAuthenticationProvider
        );
    }

}
