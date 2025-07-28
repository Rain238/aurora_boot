package top.rainrem.boot.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.rainrem.boot.auth.model.CaptchaInfo;
import top.rainrem.boot.auth.model.dto.WxMiniAppCodeLoginDTO;
import top.rainrem.boot.auth.model.dto.WxMiniAppPhoneLoginDTO;
import top.rainrem.boot.auth.service.AuthService;
import top.rainrem.boot.common.annotation.Log;
import top.rainrem.boot.common.enums.LogModuleEnum;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.core.security.model.AuthenticationToken;

/**
 * 认证控制层
 *
 * @author LightRain
 * @since 2025年7月25日18:54:08
 */
@Slf4j
@Tag(name = "01.认证中心")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * 业务层
     */
    private final AuthService authService;

    @GetMapping("/captcha")
    @Operation(summary = "获取验证码")
    public Result<CaptchaInfo> getCaptcha() {
        CaptchaInfo captcha = authService.getCaptcha();
        return Result.success(captcha);
    }

    @PostMapping("/login")
    @Operation(summary = "账号密码登录")
    @Log(value = "登录", module = LogModuleEnum.LOGIN)
    public Result<AuthenticationToken> login(
            @Parameter(description = "用户名", example = "admin") @RequestParam String username,
            @Parameter(description = "密码", example = "123456") @RequestParam String password
    ) {
        AuthenticationToken authenticationToken = authService.login(username, password);
        return Result.success(authenticationToken);
    }

    @PostMapping("/login/sms")
    @Operation(summary = "短信验证码登录")
    @Log(value = "短信验证码登录", module = LogModuleEnum.LOGIN)
    public Result<AuthenticationToken> loginBySms(
            @Parameter(description = "手机号", example = "16678912143") @RequestParam String mobile,
            @Parameter(description = "验证码", example = "1234") @RequestParam String code
    ) {
        AuthenticationToken loginResult = authService.loginBySms(mobile, code);
        return Result.success(loginResult);
    }

    @PostMapping("/sms/code")
    @Operation(summary = "发送登录短信验证码")
    public Result<Void> sendLoginVerifyCode(
            @Parameter(description = "手机号", example = "18812345678") @RequestParam String mobile
    ) {
        authService.sendSmsLoginCode(mobile);
        return Result.success();
    }

    @PostMapping("/login/wechat")
    @Operation(summary = "微信授权登录(Web)")
    @Log(value = "微信登录", module = LogModuleEnum.LOGIN)
    public Result<AuthenticationToken> loginByWechat(
            @Parameter(description = "微信授权码", example = "code") @RequestParam String code
    ) {
        AuthenticationToken loginResult = authService.loginByWechat(code);
        return Result.success(loginResult);
    }

    @PostMapping("/wx/miniapp/code-login")
    @Operation(summary = "微信小程序登录(Code)")
    public Result<AuthenticationToken> loginByWxMiniAppCode(@RequestBody @Valid WxMiniAppCodeLoginDTO loginDTO) {
        AuthenticationToken token = authService.loginByWxMiniAppCode(loginDTO);
        return Result.success(token);
    }

    @PostMapping("/wx/miniapp/phone-login")
    @Operation(summary = "微信小程序登录(手机号)")
    public Result<AuthenticationToken> loginByWxMiniAppPhone(@RequestBody @Valid WxMiniAppPhoneLoginDTO loginDTO) {
        AuthenticationToken token = authService.loginByWxMiniAppPhone(loginDTO);
        return Result.success(token);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "退出登录")
    @Log(value = "退出登录", module = LogModuleEnum.LOGIN)
    public Result<?> logout() {
        authService.logout();
        return Result.success();
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    public Result<?> refreshToken(
            @Parameter(description = "刷新令牌", example = "xxx.xxx.xxx") @RequestParam String refreshToken
    ) {
        AuthenticationToken authenticationToken = authService.refreshToken(refreshToken);
        return Result.success(authenticationToken);
    }

}
