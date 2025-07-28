package top.rainrem.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.rainrem.boot.common.annotation.Log;
import top.rainrem.boot.common.enums.LogModuleEnum;
import top.rainrem.boot.common.result.PageResult;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.system.model.form.ConfigForm;
import top.rainrem.boot.system.model.query.ConfigPageQuery;
import top.rainrem.boot.system.model.vo.ConfigVO;
import top.rainrem.boot.system.service.ConfigService;

/**
 * 系统配置前端控制层
 *
 * @author LightRain
 * @since 2025年7月27日13:01:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "07.系统配置")
@RequestMapping("/api/v1/config")
public class ConfigController {

    /**
     * 系统配置
     */
    private final ConfigService configService;

    @GetMapping("/page")
    @Operation(summary = "系统配置分页列表")
    @PreAuthorize("@ss.hasPerm('sys:config:query')")
    @Log( value = "系统配置分页列表",module = LogModuleEnum.SETTING)
    public PageResult<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = configService.page(configPageQuery);
        return PageResult.success(result);
    }

    @PostMapping
    @Operation(summary = "新增系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:add')")
    @Log( value = "新增系统配置",module = LogModuleEnum.SETTING)
    public Result<?> save(@RequestBody @Valid ConfigForm configForm) {
        return Result.judge(configService.save(configForm));
    }

    @GetMapping("/{id}/form")
    @Operation(summary = "获取系统配置表单数据")
    public Result<ConfigForm> getConfigForm(@Parameter(description = "系统配置ID") @PathVariable Long id) {
        ConfigForm formData = configService.getConfigFormData(id);
        return Result.success(formData);
    }

    @PutMapping("/refresh")
    @Operation(summary = "刷新系统配置缓存")
    @PreAuthorize("@ss.hasPerm('sys:config:refresh')")
    @Log( value = "刷新系统配置缓存",module = LogModuleEnum.SETTING)
    public Result<ConfigForm> refreshCache() {
        return Result.judge(configService.refreshCache());
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "修改系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:update')")
    @Log( value = "修改系统配置",module = LogModuleEnum.SETTING)
    public Result<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return Result.judge(configService.edit(id, configForm));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:delete')")
    @Log( value = "删除系统配置",module = LogModuleEnum.SETTING)
    public Result<?> delete(@PathVariable Long id) {
        return Result.judge(configService.delete(id));
    }

}
