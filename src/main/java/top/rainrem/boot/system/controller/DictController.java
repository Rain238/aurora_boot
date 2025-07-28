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
import top.rainrem.boot.system.model.form.DictForm;
import top.rainrem.boot.system.model.form.DictItemForm;
import top.rainrem.boot.system.model.query.DictItemPageQuery;
import top.rainrem.boot.system.model.query.DictPageQuery;
import top.rainrem.boot.system.model.vo.DictItemOptionVO;
import top.rainrem.boot.system.model.vo.DictItemPageVO;
import top.rainrem.boot.system.model.vo.DictPageVO;
import top.rainrem.boot.system.service.DictItemService;
import top.rainrem.boot.system.service.DictService;
import top.rainrem.boot.system.service.WebSocketService;

import java.util.Arrays;
import java.util.List;

/**
 * 字典控制层
 *
 * @author LightRain
 * @since 2025年7月26日20:03:30
 */
@Tag(name = "05.字典接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dicts")
@SuppressWarnings("SpellCheckingInspection")
public class DictController {

    /**
     * 字典业务
     */
    private final DictService dictService;

    /**
     * 字典项
     */
    private final DictItemService dictItemService;

    /**
     * WebSocket服务接口
     */
    private final WebSocketService webSocketService;

    //---------------------------------------------------
    // 字典相关接口
    //---------------------------------------------------
    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    @Log(value = "字典分页列表", module = LogModuleEnum.DICT)
    public PageResult<DictPageVO> getDictPage(DictPageQuery queryParams) {
        Page<DictPageVO> result = dictService.getDictPage(queryParams);
        return PageResult.success(result);
    }


    @Operation(summary = "字典列表")
    @GetMapping
    public Result<List<Option<String>>> getDictList() {
        List<Option<String>> list = dictService.getDictList();
        return Result.success(list);
    }

    @Operation(summary = "获取字典表单数据")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(@Parameter(description = "字典ID") @PathVariable Long id) {
        DictForm formData = dictService.getDictForm(id);
        return Result.success(formData);
    }

    @PostMapping
    @RepeatSubmit
    @Operation(summary = "新增字典")
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    public Result<?> saveDict(@Valid @RequestBody DictForm formData) {
        boolean result = dictService.saveDict(formData);
        return Result.judge(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改字典")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result<?> updateDict(@PathVariable Long id, @RequestBody DictForm dictForm) {
        boolean status = dictService.updateDict(id, dictForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result<?> deleteDictionaries(@Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids) {
        // 获取字典编码列表，用于发送删除通知
        List<String> dictCodes = dictService.getDictCodesByIds(Arrays.stream(ids.split(",")).toList());
        dictService.deleteDictByIds(Arrays.stream(ids.split(",")).toList());
        return Result.success();
    }

    //---------------------------------------------------
    // 字典项相关接口
    //---------------------------------------------------
    @Operation(summary = "字典项分页列表")
    @GetMapping("/{dictCode}/items/page")
    public PageResult<DictItemPageVO> getDictItemPage(@PathVariable String dictCode, DictItemPageQuery queryParams) {
        queryParams.setDictCode(dictCode);
        Page<DictItemPageVO> result = dictItemService.getDictItemPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典项列表")
    @GetMapping("/{dictCode}/items")
    public Result<List<DictItemOptionVO>> getDictItems(@Parameter(description = "字典编码") @PathVariable String dictCode) {
        List<DictItemOptionVO> list = dictItemService.getDictItems(dictCode);
        return Result.success(list);
    }

    @RepeatSubmit
    @Operation(summary = "新增字典项")
    @PostMapping("/{dictCode}/items")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:add')")
    public Result<Void> saveDictItem(@PathVariable String dictCode, @Valid @RequestBody DictItemForm formData) {
        formData.setDictCode(dictCode);
        boolean result = dictItemService.saveDictItem(formData);
        // 发送字典更新通知
        return Result.judge(result);
    }

    @Operation(summary = "字典项表单数据")
    @GetMapping("/{dictCode}/items/{itemId}/form")
    public Result<DictItemForm> getDictItemForm(@PathVariable String dictCode, @Parameter(description = "字典项ID") @PathVariable Long itemId) {
        DictItemForm formData = dictItemService.getDictItemForm(itemId);
        return Result.success(formData);
    }

    @RepeatSubmit
    @Operation(summary = "修改字典项")
    @PutMapping("/{dictCode}/items/{itemId}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:edit')")
    public Result<?> updateDictItem(@PathVariable String dictCode, @PathVariable Long itemId, @RequestBody DictItemForm formData) {
        formData.setId(itemId);
        formData.setDictCode(dictCode);
        boolean status = dictItemService.updateDictItem(formData);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典项")
    @DeleteMapping("/{dictCode}/items/{itemIds}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:delete')")
    public Result<Void> deleteDictItems(@PathVariable String dictCode, @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String itemIds) {
        dictItemService.deleteDictItemByIds(itemIds);
        return Result.success();
    }

}
