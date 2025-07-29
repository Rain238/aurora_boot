package top.rainrem.boot.core.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.rainrem.boot.common.constant.SecurityConstants;
import top.rainrem.boot.common.constant.SystemConstants;
import top.rainrem.boot.core.security.model.SysUserDetails;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security 工具类
 *
 * @author LightRain
 * @since 2025年7月23日15:18:59
 */
public class SecurityUtils {

    /**
     * 获取当前登录人信息
     *
     * @return Optional<SysUserDetails>
     */
    public static Optional<SysUserDetails> getUser() {
        // 从当前线程的 SecurityContext 中获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 判断是否不为空
        if (authentication != null) {
            // 获取当前认证对象的主体 (通常是用户信息)
            Object principal = authentication.getPrincipal();
            // 判断主体是否是我们自定义的 SysUserDetails 类型
            if (principal instanceof SysUserDetails) {
                // 强转为 SysUserDetails 并用 Optional 包装返回
                return Optional.of((SysUserDetails) principal);
            }
        }
        // 如果未登录或主体不是 SysUserDetails，返回空 Optional
        return Optional.empty();
    }

    /**
     * 获取用户ID
     *
     * @return Long
     */
    public static Long getUserId() {
        return getUser().map(SysUserDetails::getUserId).orElse(null);
    }

    /**
     * 获取用户账号
     *
     * @return String 用户账号
     */
    public static String getUsername() {
        return getUser().map(SysUserDetails::getUsername).orElse(null);
    }

    /**
     * 获取数据权限范围
     *
     * @return Integer
     */
    public static Integer getDataScope() {
        return getUser().map(SysUserDetails::getDataScope).orElse(null);
    }

    /**
     * 获取角色集合
     *
     * @return 角色集合
     */
    public static Set<String> getRoles() {
        // 获取当前认证对象的权限列表，提取出用户的角色集合（去掉前缀 ROLE_），并返回 Set<String>
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                // 如果 authentication 存在，获取权限列表
                .map(Authentication::getAuthorities)
                // 过滤掉空集合
                .filter(CollectionUtil::isNotEmpty)
                // 将 Optional<Collection> 转成 Stream<Collection>，方便后续 flatMap
                .stream()
                // 扁平化，把权限集合中的每个 GrantedAuthority 展平为 Stream<GrantedAuthority>
                .flatMap(Collection::stream)
                // 提取每个权限的字符串标识
                .map(GrantedAuthority::getAuthority)
                // 过滤出以 ROLE_ 开头的权限（只保留角色，排除其他权限）
                .filter(authority -> authority.startsWith(SecurityConstants.ROLE_PREFIX))
                // 去掉前缀 ROLE_，只保留实际的角色名
                .map(authority -> StrUtil.removePrefix(authority, SecurityConstants.ROLE_PREFIX))
                // 收集成 Set<String> 返回
                .collect(Collectors.toSet());
    }

    /**
     * 是否超级管理员
     * <p>
     * 超级管理员忽视任何权限判断
     */
    public static boolean isRoot() {
        Set<String> roles = getRoles();
        return roles.contains(SystemConstants.ROOT_ROLE_CODE);
    }

    /**
     * 获取请求中的 Token
     *
     * @return Token 字符串
     */
    public static String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

}
