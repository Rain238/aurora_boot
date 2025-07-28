package top.rainrem.boot.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.rainrem.boot.common.annotation.Log;
import top.rainrem.boot.common.annotation.RepeatSubmit;
import top.rainrem.boot.common.enums.LogModuleEnum;
import top.rainrem.boot.common.model.Option;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.system.model.form.MenuForm;
import top.rainrem.boot.system.model.query.MenuQuery;
import top.rainrem.boot.system.model.vo.MenuVO;
import top.rainrem.boot.system.model.vo.RouteVO;
import top.rainrem.boot.system.service.MenuService;

import java.util.List;

/**
 * 菜单控制器层
 *
 * @author LightRain
 * @since 2025年7月26日17:42:03
 */
@Tag(name = "04.菜单接口")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

    /**
     * 菜单业务
     */
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "菜单列表")
    @Log( value = "菜单列表",module = LogModuleEnum.MENU)
    public Result<List<MenuVO>> listMenus(MenuQuery queryParams) {
        List<MenuVO> menuList = menuService.listMenus(queryParams);
        return Result.success(menuList);
    }

    @GetMapping("/options")
    @Operation(summary = "菜单下拉列表")
    public Result<List<Option<Long>>> listMenuOptions(
            @Parameter(description = "是否只查询父级菜单")
            @RequestParam(required = false, defaultValue = "false") boolean onlyParent
    ) {
        List<Option<Long>> menus = menuService.listMenuOptions(onlyParent);
        return Result.success(menus);
    }

    @GetMapping("/routes")
    @Operation(summary = "菜单路由列表")
    public Result<List<RouteVO>> getCurrentUserRoutes() {
        List<RouteVO> routeList = menuService.getCurrentUserRoutes();
        return Result.success(routeList);
    }

    @GetMapping("/{id}/form")
    @Operation(summary = "菜单表单数据")
    public Result<MenuForm> getMenuForm(@Parameter(description = "菜单ID") @PathVariable Long id) {
        MenuForm menu = menuService.getMenuForm(id);
        return Result.success(menu);
    }

    @PostMapping
    @RepeatSubmit
    @Operation(summary = "新增菜单")
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    public Result<?> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    public Result<?> updateMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuService.saveMenu(menuForm);
        return Result.judge(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    public Result<?> deleteMenu(@Parameter(description = "菜单ID，多个以英文(,)分割") @PathVariable("id") Long id) {
        boolean result = menuService.deleteMenu(id);
        return Result.judge(result);
    }

    @PatchMapping("/{menuId}")
    @Operation(summary = "修改菜单显示状态")
    public Result<?> updateMenuVisible(
            @Parameter(description = "菜单ID") @PathVariable Long menuId,
            @Parameter(description = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result = menuService.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }

}
