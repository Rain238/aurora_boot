package top.rainrem.boot.core.security.extension.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.rainrem.boot.core.security.model.SysUserDetails;
import top.rainrem.boot.core.security.model.UserAuthCredentials;
import top.rainrem.boot.system.service.UserService;

/**
 * 微信小程序Code认证Provider
 * @author LightRain
 * @since 2025年7月25日19:59:56
 */
@Slf4j
public class WxMiniAppCodeAuthenticationProvider  implements AuthenticationProvider {

    /**
     * 用户服务接口
     */
    private final UserService userService;

    /**
     * 微信服务接口
     */
    private final WxMaService wxMaService;

    public WxMiniAppCodeAuthenticationProvider(UserService userService, WxMaService wxMaService) {
        this.userService = userService;
        this.wxMaService = wxMaService;
    }

    /**
     * 微信认证逻辑 参考 Spring Security 认证密码校验流程
     * @param authentication 认证对象
     * @return 认证后的 Authentication 对象
     * @throws AuthenticationException 认证异常
     * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 从主体中获取 code
        String code = (String) authentication.getPrincipal();
        // 通过微信服务端验证 code 并获取用户会话信息
        WxMaJscode2SessionResult sessionInfo;
        try {
            sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            throw new CredentialsExpiredException("微信登录 code 无效或已失效，请重新获取");
        }
        String openId = sessionInfo.getOpenid();
        if (StrUtil.isBlank(openId)) {
            throw new UsernameNotFoundException("未能获取到微信 OpenID，请稍后重试");
        }
        // 根据微信 OpenID 查询用户信息
        UserAuthCredentials userAuthCredentials = userService.getAuthCredentialsByOpenId(openId);
        if (userAuthCredentials == null) {
            // 用户不存在则注册
            userService.registerOrBindWechatUser(openId);

            // 再次查询用户信息，确保用户注册成功
            userAuthCredentials = userService.getAuthCredentialsByOpenId(openId);
            if (userAuthCredentials == null) {
                throw new UsernameNotFoundException("用户注册失败，请稍后重试");
            }
        }
        // 检查用户状态是否有效
        if (ObjectUtil.notEqual(userAuthCredentials.getStatus(), 1)) {
            throw new DisabledException("用户已被禁用");
        }
        // 构建认证后的用户详情信息
        SysUserDetails userDetails = new SysUserDetails(userAuthCredentials);
        // 创建已认证的Token
        return WxMiniAppCodeAuthenticationToken.authenticated(
                userDetails,
                userDetails.getAuthorities()
        );
    }

    /**
     * 判断当前认证提供者是否支持指定类型的认证对象。
     *
     * @param authentication 传入的认证类对象，通常由 Spring Security 在认证流程中提供
     * @return 如果该认证提供者支持处理传入的认证类型，则返回 true，否则返回 false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 判断传入的认证类是否是 WxMiniAppCodeAuthenticationToken 类型或其子类
        return WxMiniAppCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
