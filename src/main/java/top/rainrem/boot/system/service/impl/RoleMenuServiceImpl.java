package top.rainrem.boot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.system.enums.MenuTypeEnum;
import top.rainrem.boot.system.mapper.RoleMenuMapper;
import top.rainrem.boot.system.model.bo.RolePermsBO;
import top.rainrem.boot.system.model.entity.RoleMenu;
import top.rainrem.boot.system.service.RoleMenuService;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单服务实现类
 *
 * @author LightRain
 * @since 2025年7月23日18:31:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    /**
     * Redis模版
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化权限缓存
     */
    @PostConstruct
    public void initRolePermsCache() {
        log.info("初始化权限缓存... ");
        refreshRolePermsCache();
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache() {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, "*");

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(null, MenuTypeEnum.BUTTON.getValue());
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                String roleCode = item.getRoleCode();
                Set<String> perms = item.getPerms();
                if (CollectionUtil.isNotEmpty(perms)) {
                    redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleCode, perms);
                }
            });
        }
    }

    /**
     * 刷新权限缓存
     */
    @Override
    public void refreshRolePermsCache(String roleCode) {
        // 清理权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, roleCode);

        List<RolePermsBO> list = this.baseMapper.getRolePermsList(roleCode,MenuTypeEnum.BUTTON.getValue());
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            if (CollectionUtil.isNotEmpty(perms)) {
                redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, roleCode, perms);
            }
        }
    }

    /**
     * 刷新权限缓存 (角色编码变更时调用)
     */
    @Override
    public void refreshRolePermsCache(String oldRoleCode, String newRoleCode) {
        // 清理旧角色权限缓存
        redisTemplate.opsForHash().delete(RedisConstants.System.ROLE_PERMS, oldRoleCode);

        // 添加新角色权限缓存
        List<RolePermsBO> list = this.baseMapper.getRolePermsList(newRoleCode,MenuTypeEnum.BUTTON.getValue());
        if (CollectionUtil.isNotEmpty(list)) {
            RolePermsBO rolePerms = list.get(0);
            if (rolePerms == null) {
                return;
            }

            Set<String> perms = rolePerms.getPerms();
            redisTemplate.opsForHash().put(RedisConstants.System.ROLE_PERMS, newRoleCode, perms);
        }
    }

    /**
     * 获取角色权限集合
     *
     * @param roles 角色编码集合
     * @return 权限集合
     */
    @Override
    public Set<String> getRolePermsByRoleCodes(Set<String> roles) {
        return this.baseMapper.listRolePerms(roles);
    }

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.listMenuIdsByRoleId(roleId);
    }
}
