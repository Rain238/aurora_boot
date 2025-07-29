package top.rainrem.boot.core.security.extension.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
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
 * 微信小程序手机号认证Provider
 *
 * @author LightRain
 * @since 2025年7月25日20:18:09
 */
@Slf4j
public class WxMiniAppPhoneAuthenticationProvider implements AuthenticationProvider {
    /**
     * 用户服务接口
     */
    private final UserService userService;

    /**
     * 微信服务接口
     */
    private final WxMaService wxMaService;

    public WxMiniAppPhoneAuthenticationProvider(UserService userService, WxMaService wxMaService) {
        this.userService = userService;
        this.wxMaService = wxMaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 微信小程序手机号认证Token
        WxMiniAppPhoneAuthenticationToken authenticationToken = (WxMiniAppPhoneAuthenticationToken) authentication;
        // 从主体获取 code
        String code = (String) authenticationToken.getPrincipal();
        // 获取加密数据
        String encryptedData = authenticationToken.getEncryptedData();
        // 获取初始向量
        String iv = authenticationToken.getIv();
        // 1. 通过code获取session_key
        WxMaJscode2SessionResult sessionInfo;
        try {
            sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            log.error("获取微信session_key失败", e);
            throw new CredentialsExpiredException("微信登录code无效或已过期");
        }
        String sessionKey = sessionInfo.getSessionKey();
        String openId = sessionInfo.getOpenid();
        if (StrUtil.isBlank(sessionKey) || StrUtil.isBlank(openId)) {
            throw new CredentialsExpiredException("获取微信会话信息失败");
        }
        // 2. 解密手机号信息
        WxMaPhoneNumberInfo phoneNumberInfo;
        try {
            if (StrUtil.isNotBlank(encryptedData) && StrUtil.isNotBlank(iv)) {
//                phoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
                phoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(code);
            } else {
                throw new IllegalArgumentException("缺少手机号加密数据");
            }
        } catch (Exception e) {
            log.error("解密微信手机号失败", e);
            throw new CredentialsExpiredException("解密手机号信息失败");
        }
        if (phoneNumberInfo == null || StrUtil.isBlank(phoneNumberInfo.getPhoneNumber())) {
            throw new CredentialsExpiredException("获取手机号失败");
        }
        // 获取电话号码
        String phoneNumber = phoneNumberInfo.getPhoneNumber();
        // 3. 根据手机号查询用户，不存在则创建新用户
        UserAuthCredentials userAuthCredentials = userService.getAuthCredentialsByMobile(phoneNumber);
        if (userAuthCredentials == null) {
            // 用户不存在，注册新用户
            boolean registered = userService.registerUserByMobileAndOpenId(phoneNumber, openId);
            if (!registered) {
                throw new UsernameNotFoundException("用户注册失败");
            }
            // 重新获取用户信息
            userAuthCredentials = userService.getAuthCredentialsByMobile(phoneNumber);
        } else {
            // 用户存在，绑定openId（如果未绑定）
            userService.bindUserOpenId(userAuthCredentials.getUserId(), openId);
        }
        // 4. 检查用户状态
        if (ObjectUtil.notEqual(userAuthCredentials.getStatus(), 1)) {
            throw new DisabledException("用户已被禁用");
        }
        // 5. 构建认证后的用户详情
        SysUserDetails userDetails = new SysUserDetails(userAuthCredentials);

        // 6. 创建已认证的Token
        return WxMiniAppPhoneAuthenticationToken.authenticated(
                userDetails,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WxMiniAppPhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
