package top.rainrem.boot.auth.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.rainrem.boot.auth.enums.CaptchaTypeEnum;
import top.rainrem.boot.auth.model.CaptchaInfo;
import top.rainrem.boot.auth.model.dto.WxMiniAppCodeLoginDTO;
import top.rainrem.boot.auth.model.dto.WxMiniAppPhoneLoginDTO;
import top.rainrem.boot.auth.service.AuthService;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.common.constant.SecurityConstants;
import top.rainrem.boot.config.property.CaptchaProperties;
import top.rainrem.boot.core.security.extension.sms.SmsAuthenticationToken;
import top.rainrem.boot.core.security.model.AuthenticationToken;
import top.rainrem.boot.core.security.token.TokenManager;
import top.rainrem.boot.core.security.extension.wx.WxMiniAppCodeAuthenticationToken;
import top.rainrem.boot.core.security.extension.wx.WxMiniAppPhoneAuthenticationToken;
import top.rainrem.boot.core.util.SecurityUtils;
import top.rainrem.boot.shared.sms.enums.SmsTypeEnum;
import top.rainrem.boot.shared.sms.service.SmsService;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 *
 * @author LightRain
 * @since 2025年7月22日20:34:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * 身份验证管理器
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 验证码属性配置
     */
    private final CaptchaProperties captchaProperties;

    /**
     * 代码生成器
     */
    private final CodeGenerator codeGenerator;

    /**
     * Redis模版
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 字体
     */
    private final Font captchaFont;

    /**
     * Token管理器
     */
    private final TokenManager tokenManager;

    /**
     * 短信服务
     */
    private final SmsService smsService;


    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 访问令牌
     */
    @Override
    public AuthenticationToken login(String username, String password) {
        // 创建用户密码认证的令牌 (未认证)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.trim(), password);

        // 执行认证 (认证中)
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 认证成功后生成 JWT 令牌，并存入 Security 上下文中,供登录日志 AOP 使用 (已认证)
        AuthenticationToken authenticationTokenResponse = tokenManager.generateToken(authenticate);

        // 设置身份验证
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return authenticationTokenResponse;
    }

    /**
     * 注销登录
     */
    @Override
    public void logout() {
        // 从请求中获取Token
        String token = SecurityUtils.getTokenFromRequest();
        if (StrUtil.isNotBlank(token) && token.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            // 截取出Token值
            token = token.substring(SecurityConstants.BEARER_TOKEN_PREFIX.length());
            // 将 JWT 令牌加入到黑名单
            tokenManager.invalidateToken(token);
            // 清除 Security 上下文中
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    @Override
    public CaptchaInfo getCaptcha() {
        // 获取验证码实例（根据配置生成具体类型的验证码对象，如圆圈、GIF、线条等）
        AbstractCaptcha captcha = getAbstractCaptcha();
        // 设置验证码生成器（用于生成验证码文本）
        captcha.setGenerator(codeGenerator);
        // 设置验证码文本透明度
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        // 设置验证码字体
        captcha.setFont(captchaFont);
        // 获取生成的验证码文本（例如 "a7dK"）
        String captchaCode = captcha.getCode();
        // 获取验证码对应的图片 Base64 编码数据（前端可直接展示该图片）
        String imageBase64Data = captcha.getImageBase64Data();
        // 生成唯一标识符，用作验证码缓存的 key
        String captchaKey = IdUtil.fastSimpleUUID();
        // 将验证码文本缓存至 Redis，用于后续登录时校验
        redisTemplate.opsForValue().set(
                // Redis 中的 key，例如 "captcha:image_code:1e2f3g4h5i"
                StrUtil.format(RedisConstants.Captcha.IMAGE_CODE, captchaKey),
                captchaCode,                            // 验证码文本
                captchaProperties.getExpireSeconds(),   // 设置过期时间（秒）
                TimeUnit.SECONDS                        // 以秒为单位
        );

        // 构建 CaptchaInfo 对象返回给前端（包含 key 和 base64 图片数据）
        return CaptchaInfo.builder()
                .captchaKey(captchaKey)            // 用于前端提交校验
                .captchaBase64(imageBase64Data)    // 用于前端展示验证码图片
                .build();

    }

    private @NotNull AbstractCaptcha getAbstractCaptcha() {
        // 从配置文件中读取验证码相关的属性
        String captchaType = captchaProperties.getType();             // 验证码类型（如：CIRCLE, GIF, LINE, SHEAR）
        int width = captchaProperties.getWidth();                     // 验证码图片宽度
        int height = captchaProperties.getHeight();                   // 验证码图片高度
        int interfereCount = captchaProperties.getInterfereCount();   // 干扰元素数量
        int codeLength = captchaProperties.getCode().getLength();     // 验证码字符长度
        AbstractCaptcha captcha;
        // 根据配置的验证码类型生成对应类型的验证码
        if (CaptchaTypeEnum.CIRCLE.name().equalsIgnoreCase(captchaType)) {
            // 圆圈干扰验证码
            captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.GIF.name().equalsIgnoreCase(captchaType)) {
            // 动态 GIF 验证码（不支持干扰数量）
            captcha = CaptchaUtil.createGifCaptcha(width, height, codeLength);
        } else if (CaptchaTypeEnum.LINE.name().equalsIgnoreCase(captchaType)) {
            // 线条干扰验证码
            captcha = CaptchaUtil.createLineCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.SHEAR.name().equalsIgnoreCase(captchaType)) {
            // 扭曲干扰验证码
            captcha = CaptchaUtil.createShearCaptcha(width, height, codeLength, interfereCount);
        } else {
            // 如果配置的类型不支持，抛出异常
            throw new IllegalArgumentException("Invalid captcha type: " + captchaType);
        }
        // 返回生成的验证码对象
        return captcha;
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌
     */
    @Override
    public AuthenticationToken refreshToken(String refreshToken) {
        return tokenManager.refreshToken(refreshToken);
    }

    /**
     * 微信一键授权登录
     *
     * @param code 微信登录code
     * @return 访问令牌
     */
    @Override
    public AuthenticationToken loginByWechat(String code) {
        // 1. 创建用户微信认证的令牌（未认证）
        WxMiniAppCodeAuthenticationToken authenticationToken = new WxMiniAppCodeAuthenticationToken(code);

        // 2. 执行认证（认证中）
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 认证成功后生成 JWT 令牌，并存入 Security 上下文，供登录日志 AOP 使用（已认证）
        AuthenticationToken token = tokenManager.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    /**
     * 微信小程序Code登录
     *
     * @param loginDTO 登录参数
     * @return 访问令牌
     */
    @Override
    public AuthenticationToken loginByWxMiniAppCode(WxMiniAppCodeLoginDTO loginDTO) {
        // 1. 创建微信小程序认证令牌（未认证）
        WxMiniAppCodeAuthenticationToken authenticationToken = new WxMiniAppCodeAuthenticationToken(loginDTO.getCode());

        // 2. 执行认证（认证中）
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 认证成功后生成 JWT 令牌，并存入 Security 上下文，供登录日志 AOP 使用（已认证）
        AuthenticationToken token = tokenManager.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    /**
     * 微信小程序手机号登录
     *
     * @param loginDTO 登录参数
     * @return 访问令牌
     */
    @Override
    public AuthenticationToken loginByWxMiniAppPhone(WxMiniAppPhoneLoginDTO loginDTO) {
        // 创建微信小程序手机号认证Token
        WxMiniAppPhoneAuthenticationToken authenticationToken = new WxMiniAppPhoneAuthenticationToken(
                loginDTO.getCode(),
                loginDTO.getEncryptedData(),
                loginDTO.getIv()
        );

        // 执行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 认证成功后生成JWT令牌，并存入Security上下文
        AuthenticationToken token = tokenManager.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    /**
     * 发送登录短信验证码
     *
     * @param mobile 手机号
     */
    @Override
    public void sendSmsLoginCode(String mobile) {
        // 随机生成4位验证码
        // String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        // TODO 为了方便测试，验证码固定为 1234，实际开发中在配置了厂商短信服务后，可以使用上面的随机验证码
        String code = "1234";

        // 发送短信验证码
        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("code", code);
        try {
            smsService.sendSms(mobile, SmsTypeEnum.LOGIN, templateParams);
        } catch (Exception e) {
            log.error("发送短信验证码失败", e);
        }
        // 缓存验证码至Redis，用于登录校验
        redisTemplate.opsForValue().set(StrUtil.format(RedisConstants.Captcha.SMS_LOGIN_CODE, mobile), code, 5, TimeUnit.MINUTES);
    }

    /**
     * 短信验证码登录
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return 访问令牌
     */
    @Override
    public AuthenticationToken loginBySms(String mobile, String code) {
        // 1. 创建用户短信验证码认证的令牌（未认证）
        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(mobile, code);

        // 2. 执行认证（认证中）
        Authentication authentication = authenticationManager.authenticate(smsAuthenticationToken);

        // 3. 认证成功后生成 JWT 令牌，并存入 Security 上下文，供登录日志 AOP 使用（已认证）
        AuthenticationToken authenticationToken = tokenManager.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authenticationToken;
    }
}
