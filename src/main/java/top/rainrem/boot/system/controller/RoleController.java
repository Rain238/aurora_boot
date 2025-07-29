package top.rainrem.boot.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.rainrem.boot.common.annotation.Log;
import top.rainrem.boot.common.annotation.RepeatSubmit;
import top.rainrem.boot.common.enums.LogModuleEnum;
import top.rainrem.boot.common.model.Option;
import top.rainrem.boot.common.result.PageResult;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.system.model.form.RoleForm;
import top.rainrem.boot.system.model.query.RolePageQuery;
import top.rainrem.boot.system.model.vo.RolePageVO;
import top.rainrem.boot.system.service.RoleService;

import java.util.List;

/**
 * 角色控制层
 *
 * @author LightRain
 * @since 2025年7月26日17:41:18
 */
@Tag(name = "03.角色接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    /**
     * 角色业务
     */
    private final RoleService roleService;

    @GetMapping("/page")
    @Operation(summary = "角色分页列表")
    @Log(value = "角色分页列表", module = LogModuleEnum.ROLE)
    public PageResult<RolePageVO> getRolePage(RolePageQuery queryParams) {
        Page<RolePageVO> result = roleService.getRolePage(queryParams);
        return PageResult.success(result);
    }

    @GetMapping("/options")
    @Operation(summary = "角色下拉列表")
    public Result<List<Option<Long>>> listRoleOptions() {
        List<Option<Long>> list = roleService.listRoleOptions();
        return Result.success(list);
    }

    @RepeatSubmit
    @PostMapping
    @Operation(summary = "新增角色")
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    public Result<?> addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @GetMapping("/{roleId}/form")
    @Operation(summary = "获取角色表单数据")
    public Result<RoleForm> getRoleForm(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        RoleForm roleForm = roleService.getRoleForm(roleId);
        return Result.success(roleForm);
    }

    @Operation(summary = "修改角色")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    public Result<?> updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result<Void> deleteRoles(@Parameter(description = "删除角色，多个以英文逗号(,)拼接") @PathVariable String ids) {
        roleService.deleteRoles(ids);
        return Result.success();
    }

    @Operation(summary = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public Result<?> updateRoleStatus(@Parameter(description = "角色ID") @PathVariable Long roleId, @Parameter(description = "状态(1:启用;0:禁用)") @RequestParam Integer status) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @GetMapping("/{roleId}/menuIds")
    @Operation(summary = "获取角色的菜单ID集合")
    public Result<List<Long>> getRoleMenuIds(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        List<Long> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.success(menuIds);
    }

    @PutMapping("/{roleId}/menus")
    @Operation(summary = "角色分配菜单权限")
    public Result<Void> assignMenusToRole(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        roleService.assignMenusToRole(roleId, menuIds);
        return Result.success();
    }
}