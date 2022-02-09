package com.oket.ccic.controller;

import com.oket.ccic.service.SampleReportService;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.SampleReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: Layer
 * @description: 取样报告单控制层
 * @author: dzp
 * @create: 2021-08-21 16:03
 **/
@RestController
@RequestMapping(value = "/sample")
@Api(tags = "取样报告单控制层")
public class SampleReportController {

    @Autowired
    private SampleReportService sampleReportService;

    @GetMapping("/get")
    @ApiOperation("分页查询计算记录")
    @RequiresPermissions("samplereport:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "检定编号", name = "verificationNo") @RequestParam(value = "verificationNo", required = false) String verificationNo,
                           @ApiParam(value = "船名", name = "shipName") @RequestParam(value = "shipName", required = false) String shipName,
                           @ApiParam(value = "委托人", name = "principal") @RequestParam(value = "principal", required = false) String principal,
                           @ApiParam(value = "开始时间", name = "startTime") @RequestParam(value = "startTime", required = false) Date startTime,
                           @ApiParam(value = "结束时间", name = "endTime") @RequestParam(value = "endTime", required = false) Date endTime) {
        RespPageBean page = sampleReportService.getPage(pageNum, pageSize, verificationNo, startTime, endTime, shipName, principal);
        return Message.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加取样报告")
    @RequiresPermissions("report:tosamplereport")
    public Message add(@ApiParam(value = "取样报告单Vo", name = "sampleReportVo") @RequestBody SampleReportVo sampleReportVo) {
        sampleReportService.addSampleReport(sampleReportVo);
        return Message.ok();
    }
}
