package top.rainrem.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.rainrem.boot.common.result.PageResult;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.system.model.form.NoticeForm;
import top.rainrem.boot.system.model.query.NoticePageQuery;
import top.rainrem.boot.system.model.vo.NoticeDetailVO;
import top.rainrem.boot.system.model.vo.NoticePageVO;
import top.rainrem.boot.system.model.vo.UserNoticePageVO;
import top.rainrem.boot.system.service.NoticeService;
import top.rainrem.boot.system.service.UserNoticeService;

/**
 * 通知公告前端控制层
 *
 * @author LightRain
 * @since 2025年7月27日13:40:52
 */
@Tag(name = "08.通知公告")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    /**
     * 通知公告
     */
    private final NoticeService noticeService;

    /**
     * 用户公告状态服务
     */
    private final UserNoticeService userNoticeService;

    @GetMapping("/page")
    @Operation(summary = "通知公告分页列表")
    @PreAuthorize("@ss.hasPerm('sys:notice:query')")
    public PageResult<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        IPage<NoticePageVO> result = noticeService.getNoticePage(queryParams);
        return PageResult.success(result);
    }

    @PostMapping
    @Operation(summary = "新增通知公告")
    @PreAuthorize("@ss.hasPerm('sys:notice:add')")
    public Result<?> saveNotice(@RequestBody @Valid NoticeForm formData) {
        boolean result = noticeService.saveNotice(formData);
        return Result.judge(result);
    }

    @GetMapping("/{id}/form")
    @Operation(summary = "获取通知公告表单数据")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<NoticeForm> getNoticeForm(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        NoticeForm formData = noticeService.getNoticeFormData(id);
        return Result.success(formData);
    }

    @GetMapping("/{id}/detail")
    @Operation(summary = "阅读获取通知公告详情")
    public Result<NoticeDetailVO> getNoticeDetail(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        NoticeDetailVO detailVO = noticeService.getNoticeDetail(id);
        return Result.success(detailVO);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "修改通知公告")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<Void> updateNotice(@Parameter(description = "通知公告ID") @PathVariable Long id, @RequestBody @Validated NoticeForm formData) {
        boolean result = noticeService.updateNotice(id, formData);
        return Result.judge(result);
    }

    @PutMapping("/{id}/publish")
    @Operation(summary = "发布通知公告")
    @PreAuthorize("@ss.hasPerm('sys:notice:publish')")
    public Result<Void> publishNotice(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        boolean result = noticeService.publishNotice(id);
        return Result.judge(result);
    }

    @PutMapping("/{id}/revoke")
    @Operation(summary = "撤回通知公告")
    @PreAuthorize("@ss.hasPerm('sys:notice:revoke')")
    public Result<Void> revokeNotice(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        boolean result = noticeService.revokeNotice(id);
        return Result.judge(result);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除通知公告")
    @PreAuthorize("@ss.hasPerm('sys:notice:delete')")
    public Result<Void> deleteNotices(@Parameter(description = "通知公告ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        boolean result = noticeService.deleteNotices(ids);
        return Result.judge(result);
    }

    @PutMapping("/read-all")
    @Operation(summary = "全部已读")
    public Result<Void> readAll() {
        userNoticeService.readAll();
        return Result.success();
    }

    @GetMapping("/my-page")
    @Operation(summary = "获取我的通知公告分页列表")
    public PageResult<UserNoticePageVO> getMyNoticePage(NoticePageQuery queryParams) {
        IPage<UserNoticePageVO> result = noticeService.getMyNoticePage(queryParams);
        return PageResult.success(result);
    }

}
