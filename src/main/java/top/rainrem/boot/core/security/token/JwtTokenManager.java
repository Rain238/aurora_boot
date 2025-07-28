package top.rainrem.boot.core.security.token;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import top.rainrem.boot.common.constant.JwtClaimConstants;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.common.constant.SecurityConstants;
import top.rainrem.boot.common.exception.BusinessException;
import top.rainrem.boot.common.result.ResultCode;
import top.rainrem.boot.config.property.SecurityProperties;
import top.rainrem.boot.core.security.model.AuthenticationToken;
import top.rainrem.boot.core.security.model.SysUserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * JWT Token 管理器
 * <p>
 * 用于生成、解析、校验、刷新 JWT Token
 *
 * @author LightRain
 * @since 2025年7月22日17:53:48
 */
@Service
@ConditionalOnProperty(value = "security.session.type", havingValue = "jwt")
public class JwtTokenManager implements TokenManager {

    /**
     * 安全模块配置属性类
     */
    private final SecurityProperties securityProperties;
    /**
     * redis模版
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 秘钥*
     */
    private final byte[] secretKey;

    public JwtTokenManager(SecurityProperties securityProperties, RedisTemplate<String, Object> redisTemplate, byte[] secretKey) {
        this.securityProperties = securityProperties;
        this.redisTemplate = redisTemplate;
        this.secretKey = securityProperties.getSession().getJwt().getSecretKey().getBytes();
    }

    /**
     * 生成令牌
     *
     * @param authentication 用户认证信息
     * @return 令牌响应对象
     */
    @Override
    public AuthenticationToken generateToken(Authentication authentication) {
        // 访问令牌生存时间
        Integer accessTokenTimeToLive = securityProperties.getSession().getAccessTokenTimeToLive();
        // 刷新令牌生存时间
        Integer refreshTokenTimeToLive = securityProperties.getSession().getRefreshTokenTimeToLive();
        // 生成令牌
        String accessToken = generateToken(authentication, accessTokenTimeToLive);
        // 刷新令牌
        String refreshToken = generateToken(authentication, refreshTokenTimeToLive, true);
        return AuthenticationToken.builder()
                // 设置访问令牌（JWT access token）
                .accessToken(accessToken)
                // 设置刷新令牌（JWT refresh token）
                .refreshToken(refreshToken)
                // 设置令牌类型，通常是 OAuth2 中的 Bearer
                .tokenType("Bearer")
                // 设置访问令牌有效期（单位：秒）
                .expiresIn(accessTokenTimeToLive)
                // 构建 AuthenticationToken 对象
                .build();
    }

    /**
     * 生成 JWT Token
     *
     * @param authentication        认证信息
     * @param accessTokenTimeToLive 访问令牌生存时间
     * @return JWT Token
     */
    private String generateToken(Authentication authentication, Integer accessTokenTimeToLive) {
        return generateToken(authentication, accessTokenTimeToLive, false);
    }

    /**
     * 生成 JWT Token
     *
     * @param authentication        认证信息
     * @param accessTokenTimeToLive 过期时间
     * @param isRefreshToken        类型是否为刷新token
     * @return JWT Token
     */
    private String generateToken(Authentication authentication, Integer accessTokenTimeToLive, boolean isRefreshToken) {
        // 从 Spring Security 中取出当前登录用户，并把它转成你自己定义的用户信息类。
        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
        // 创建 Map 集合
        Map<String, Object> payload = new HashMap<>();
        // 添加用户ID
        payload.put(JwtClaimConstants.USER_ID, userDetails.getUserId());
        // 数据权限范围
        payload.put(JwtClaimConstants.DATA_SCOPE, userDetails.getDataScope());
        // claims 中添加角色信息
        Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        // 添加角色
        payload.put(JwtClaimConstants.AUTHORITIES, roles);

        // 当前时间
        Date now = new Date();
        // 创建 JWT 负载（Payload）的一部分：签发时间（iat）
        payload.put(JWTPayload.ISSUED_AT, now);
        // 设置 JWT 负载中的 token 类型标志，默认是“访问 token”
        payload.put(JwtClaimConstants.TOKEN_TYPE, false);
        // 如果当前生成的是刷新 token，则标记为 true
        if (isRefreshToken) {
            payload.put(JwtClaimConstants.TOKEN_TYPE, true);
        }
        // 设置过期时间（exp）
        // 如果 accessTokenTimeToLive == -1，则表示永不过期
        if (accessTokenTimeToLive != -1) {
            // 计算过期时间：当前时间 + 存活时间（秒）
            Date expiresAt = DateUtil.offsetSecond(now, accessTokenTimeToLive);
            payload.put(JWTPayload.EXPIRES_AT, expiresAt);
        }
        // 设置主题（sub），通常是用户名或用户唯一标识
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        // 设置 JWT 唯一 ID（jti），防止重放攻击
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());
        // 使用 secretKey 对负载进行签名并生成 JWT
        return JWTUtil.createToken(payload, secretKey);
    }

    /**
     * 解析令牌
     *
     * @param token Token
     * @return Authentication
     */
    @Override
    public Authentication parseToken(String token) {
        // 解析传入的 JWT 字符串为 JWT 对象
        JWT jwt = JWTUtil.parseToken(token);
        // 从 JWT 中提取负载部分（Payload），得到用户相关信息
        JSONObject payloads = jwt.getPayloads();
        // 创建一个自定义的用户信息对象，用于封装当前用户信息
        SysUserDetails userDetails = new SysUserDetails();
        // 从 Payload 里取出用户 ID，设置到 userDetails
        userDetails.setUserId(payloads.getLong(JwtClaimConstants.USER_ID));// 用户ID
        // 从 Payload 里取出数据权限范围，设置到 userDetails
        userDetails.setDataScope(payloads.getInt(JwtClaimConstants.DATA_SCOPE));// 数据权限范围
        // 从 Payload 里取出用户名（subject），设置到 userDetails
        userDetails.setUsername(payloads.getStr(JWTPayload.SUBJECT));// 用户名
        // 从 Payload 里取出角色/权限集合，转换为 SimpleGrantedAuthority 集合
        Set<SimpleGrantedAuthority> authorities = payloads.getJSONArray(JwtClaimConstants.AUTHORITIES)
                .stream() // 遍历 JSONArray
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority))) // 转成字符串并封装成 SimpleGrantedAuthority
                .collect(Collectors.toSet()); // 收集成 Set
        // 返回 Spring Security 所需的认证对象 UsernamePasswordAuthenticationToken
        // 包含：principal（用户信息）、credentials（密码，这里传空字符串）、authorities（权限）
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * 校验令牌
     *
     * @param token JWT Token
     * @return 是否有效
     */
    @Override
    public boolean validateToken(String token) {
        return validateToken(token, false);
    }

    /**
     * 校验刷新令牌
     *
     * @param refreshToken JWT Token
     * @return 验证结果
     */
    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, true);
    }

    /**
     * 校验 JWT Token 是否有效
     *
     * @param token                JWT 字符串
     * @param validateRefreshToken 是否校验这是一个刷新 token
     * @return 是否有效
     */
    private boolean validateToken(String token, boolean validateRefreshToken) {
        try {
            // 解析 JWT 字符串为 JWT 对象
            JWT jwt = JWTUtil.parseToken(token);
            // 检查 Token 是否有效：验签 + 是否过期
            boolean isValid = jwt.setKey(secretKey).validate(0);
            if (isValid) {
                // Token 基本有效后，继续检查黑名单
                JSONObject payloads = jwt.getPayloads();
                // 从 payload 中取出 JWT 唯一 ID (jti)
                String jti = payloads.getStr(JWTPayload.JWT_ID);
                if (validateRefreshToken) {
                    // 如果要求校验刷新 token 类型
                    boolean isRefreshToken = payloads.getBool(JwtClaimConstants.TOKEN_TYPE);
                    // 如果不是刷新 token，则返回 false
                    if (!isRefreshToken) {
                        return false;
                    }
                }
                // 判断 jti 是否在黑名单中（例如：用户注销、密码重置后被拉黑的 token）
                if (Boolean.TRUE.equals(redisTemplate.hasKey(StrUtil.format(RedisConstants.Auth.BLACKLIST_TOKEN, jti)))) {
                    // 在黑名单中，token 无效
                    return false;
                }
            }
            // 如果基础校验失败，直接返回 false
            return isValid;
        } catch (Exception e) {
            // 如果校验过程中出错，例如格式不对、解密失败，也认为无效
        }
        return false;
    }

    /**
     * 使用刷新 Token 刷新生成新的访问 Token
     *
     * @param refreshToken 客户端传来的刷新 Token
     * @return 包含新访问 Token 和原刷新 Token 的 AuthenticationToken 对象
     */
    public AuthenticationToken refreshToken(String refreshToken) {
        // 校验刷新 Token 是否有效（验签、类型、黑名单等）
        boolean isValid = validateRefreshToken(refreshToken);
        if (!isValid) {
            // 如果无效，抛出业务异常，提示刷新 Token 无效
            throw new BusinessException(ResultCode.REFRESH_TOKEN_INVALID);
        }
        // 解析刷新 Token 里的认证信息（用户名、权限等）
        Authentication authentication = parseToken(refreshToken);
        // 获取访问 Token 的有效期配置（单位：秒）
        int accessTokenExpiration = securityProperties.getSession().getAccessTokenTimeToLive();
        // 根据刷新 Token 解析出的认证信息，生成新的访问 Token
        String newAccessToken = generateToken(authentication, accessTokenExpiration);
        // 构建并返回新的 Token 对象：
        // - accessToken 是新生成的
        // - refreshToken 仍然返回原来的（因为它还没过期）
        return AuthenticationToken.builder()
                .accessToken(newAccessToken)     // 设置新的访问 Token
                .refreshToken(refreshToken)      // 返回原来的刷新 Token
                .tokenType("Bearer")             // 令牌类型固定为 Bearer
                .expiresIn(accessTokenExpiration) // 设置访问 Token 的有效期
                .build();
    }


    /**
     * 将指定的 JWT Token 作废（加入黑名单）
     *
     * @param token 前端传来的完整 Token
     */
    public void invalidateToken(String token) {
        // 如果 Token 以 Bearer 前缀开头，先去掉前缀
        if (token.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            token = token.substring(SecurityConstants.BEARER_TOKEN_PREFIX.length());
        }
        // 解析 Token
        JWT jwt = JWTUtil.parseToken(token);
        // 获取 Token 的负载信息（payload）
        JSONObject payloads = jwt.getPayloads();
        // 获取 Token 的过期时间（单位：秒，时间戳）
        Integer expirationAt = payloads.getInt(JWTPayload.EXPIRES_AT);
        // 构造黑名单 Redis Key，通常格式为: auth:blacklist:token:{jti}
        String blacklistTokenKey = StrUtil.format(
                RedisConstants.Auth.BLACKLIST_TOKEN,
                payloads.getStr(JWTPayload.JWT_ID)
        );
        if (expirationAt != null) {
            // 获取当前时间（单位：秒）
            int currentTimeSeconds = Convert.toInt(System.currentTimeMillis() / 1000);
            if (expirationAt < currentTimeSeconds) {
                // 如果 Token 已经过期，直接返回，不加入黑名单
                return;
            }
            // 计算 Token 剩余有效时间
            int expirationIn = expirationAt - currentTimeSeconds;
            // 将 Token 加入黑名单，过期时间为剩余有效时间，自动失效
            redisTemplate.opsForValue().set(blacklistTokenKey, null, expirationIn, TimeUnit.SECONDS);
        } else {
            // 如果 Token 永不过期，则永久加入黑名单
            redisTemplate.opsForValue().set(blacklistTokenKey, null);
        }
    }
}
