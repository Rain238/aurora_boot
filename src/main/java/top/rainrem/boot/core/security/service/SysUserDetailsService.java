package top.rainrem.boot.core.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.rainrem.boot.core.security.model.SysUserDetails;
import top.rainrem.boot.core.security.model.UserAuthCredentials;
import top.rainrem.boot.system.service.UserService;

/**
 * 系统用户认证
 *
 * @author LightRain
 * @since 2025年7月22日19:04:13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAuthCredentials userAuthCredentials = userService.getAuthCredentialsByUsername(username);
            if (userAuthCredentials == null) {
                throw new UsernameNotFoundException(username);
            }
            return new SysUserDetails(userAuthCredentials);
        } catch (Exception e) {
            // 记录异常日志
            log.error("认证异常:{}", e.getMessage());
            // 抛出异常
            throw e;
        }
    }
}
