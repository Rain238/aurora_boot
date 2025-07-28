package top.rainrem.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.rainrem.boot.common.constant.RedisConstants;
import top.rainrem.boot.core.util.SecurityUtils;
import top.rainrem.boot.system.converter.ConfigConverter;
import top.rainrem.boot.system.mapper.ConfigMapper;
import top.rainrem.boot.system.model.entity.Config;
import top.rainrem.boot.system.model.form.ConfigForm;
import top.rainrem.boot.system.model.query.ConfigPageQuery;
import top.rainrem.boot.system.model.vo.ConfigVO;
import top.rainrem.boot.system.service.ConfigService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统配置Service接口实现
 *
 * @author LightRain
 * @since 2025/7/22
 */
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    // 系统配置对象转换器
    private final ConfigConverter configConverter;
    // Redis模版
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 系统自动完成后加载系统配置到缓存
     */
    @PostConstruct
    public void init() {
        refreshCache();
    }

    /**
     * 分页查询系统配置
     *
     * @param sysConfigPageQuery 查询参数
     * @return 系统配置分页列表
     */
    @Override
    public IPage<ConfigVO> page(ConfigPageQuery sysConfigPageQuery) {
        // 创建分页对象，指定当前页码的每页大小
        Page<Config> page = new Page<>(sysConfigPageQuery.getPageNum(), sysConfigPageQuery.getPageSize());
        // 获取查询关键词
        String keywords = sysConfigPageQuery.getKeywords();
        // 构建查询条件:当keywords不为空时，按 config_key 或 config_name 模糊查询
        LambdaQueryWrapper<Config> query = new LambdaQueryWrapper<Config>().and(StringUtils.isNotBlank(keywords), // 只有 keywords 不为空时才拼接条件
                q -> q.like(Config::getConfigKey, keywords) // config_key 包含关键词
                        .or() // 或
                        .like(Config::getConfigName, keywords) // config_name 包含关键词
        );
        // 执行分页查询
        Page<Config> pageList = this.page(page, query);
        // 将查询结果转换为 VO 分页对象返回
        return configConverter.toPageVo(pageList);
    }

    /**
     * 保存系统配置
     *
     * @param sysConfigForm 系统配置表单
     * @return 是否保存成功
     */
    @Override
    public boolean save(ConfigForm sysConfigForm) {
        // 断言：当前数据库中不存在相同的 configKey 的记录,否则将抛出异常提示“配置键已存在”
        Assert.isTrue(
                super.count(new LambdaQueryWrapper<Config>()
                        .eq(Config::getConfigKey, sysConfigForm.getConfigKey())) == 0, "配置键已存在");
        // 将表单对象转为实体对象
        Config config = configConverter.toEntity(sysConfigForm);
        // 设置创建人用户ID
        config.setCreateBy(SecurityUtils.getUserId());
        // 设置逻辑删除标识为未删除(0 标识未删除)
        config.setIsDeleted(0);
        // 保存配置对象到数据库并返回保存结果 (true/false)
        return this.save(config);
    }

    /**
     * 获取系统配置表单数据
     * @param id 系统配置ID
     * @return 系统配置表单数据
     */
    @Override
    public ConfigForm getConfigFormData(Long id) {
        // 获取表单id
        Config entity = this.getById(id);
        return configConverter.toForm(entity);
    }

    /**
     * 编辑系统配置
     *
     * @param id         系统配置ID
     * @param configForm 系统配置表单
     * @return 是否编辑成功
     */
    @Override
    public boolean edit(Long id, ConfigForm configForm) {
        Assert.isTrue(
                super.count(new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, configForm.getConfigKey()).ne(Config::getId, id)) == 0,
                "配置键已存在");
        Config config = configConverter.toEntity(configForm);
        config.setUpdateBy(SecurityUtils.getUserId());
        return this.updateById(config);
    }

    /**
     * 删除系统配置
     *
     * @param id 系统配置ID
     * @return 是否删除成功
     */
    @Override
    public boolean delete(Long id) {
        if (id != null) {
            return super.update(new LambdaUpdateWrapper<Config>()
                    .eq(Config::getId,id)
                    .set(Config::getIsDeleted, 1)
                    .set(Config::getUpdateBy, SecurityUtils.getUserId())
            );
        }
        return false;
    }

    /**
     * 刷新系统配置缓存
     *
     * @return 是否刷新成功
     */
    @Override
    public boolean refreshCache() {
        redisTemplate.delete(RedisConstants.System.CONFIG);
        List<Config> list = this.list();
        if (list != null) {
            Map<String, String> map = list.stream().collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
            redisTemplate.opsForHash().putAll(RedisConstants.System.CONFIG, map);
            return true;
        }
        return false;
    }

    /**
     * 获取系统配置
     *
     * @param key 配置键
     * @return 配置值
     */
    @Override
    public Object getSystemConfig(String key) {
        if (StringUtils.isNotBlank(key)) {
            return redisTemplate.opsForHash().get(RedisConstants.System.CONFIG, key);
        }
        return null;
    }
}
