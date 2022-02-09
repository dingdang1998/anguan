package com.oket.ccic.controller;

import com.oket.ccic.entity.Notice;
import com.oket.ccic.service.NoticeService;
import com.oket.ccic.util.base.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 公告控制层
 * @author: dzp
 * @create: 2021-08-18 10:13
 **/
@RestController
@RequestMapping(value = "/notice")
@Api(tags = "公告控制层")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/add")
    @ApiOperation(value = "添加公告")
    @RequiresPermissions("notice:module")
    public Message add(@ApiParam(value = "公告实体", name = "notice") @RequestBody Notice notice) {
        int result = noticeService.addNotice(notice);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取公告")
    public Message get(@ApiParam(value = "开始时间", name = "startTime") @RequestParam(value = "startTime", required = false) Date startTime,
                       @ApiParam(value = "结束时间", name = "endTime") @RequestParam(value = "endTime", required = false) Date endTime,
                       @ApiParam(value = "状态", name = "status") @RequestParam(value = "status", required = false) Byte status) {
        List<Notice> notices = noticeService.get(startTime, endTime, status);
        return Message.ok(notices);
    }

    @PutMapping("/update")
    @ApiOperation(value = "编辑公告")
    @RequiresPermissions("notice:module")
    public Message update(@ApiParam(value = "公告实体", name = "notice") @RequestBody Notice notice) {
        int result = noticeService.update(notice);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update/{noticeId}/{status}")
    @ApiOperation("发布公告/取消发布")
    @RequiresPermissions("notice:module")
    public Message updateStatus(@ApiParam(value = "公告表主键id", name = "noticeId") @PathVariable Integer noticeId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = noticeService.updateNoticeStatus(noticeId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @DeleteMapping("/delete/{noticeId}")
    @ApiOperation("删除公告")
    @RequiresPermissions("notice:module")
    public Message delete(@ApiParam(value = "公告表主键id", name = "noticeId") @PathVariable Integer noticeId) {
        int result = noticeService.deleteNotice(noticeId);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
