package com.oket.ccic.controller;

import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.service.ComputingLogService;
import com.oket.ccic.service.TableService;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.ComputingLogVo;
import com.oket.ccic.vo.ProfitAndLossVo;
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
 * @description: 计算记录控制层
 * @author: dzp
 * @create: 2021-08-23 17:25
 **/
@RestController
@RequestMapping(value = "/computing")
@Api(tags = "计算记录控制层")
public class ComputingLogController {

    @Autowired
    private ComputingLogService computingLogService;
    @Autowired
    private TableService tableService;

    @GetMapping("/get")
    @ApiOperation("分页查询计算记录")
    @RequiresPermissions("computinglog:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "检定编号", name = "verificationNo") @RequestParam(value = "verificationNo", required = false) String verificationNo,
                           @ApiParam(value = "船名", name = "shipName") @RequestParam(value = "shipName", required = false) String shipName,
                           @ApiParam(value = "委托人", name = "principal") @RequestParam(value = "principal", required = false) String principal,
                           @ApiParam(value = "开始时间", name = "startTime") @RequestParam(value = "startTime", required = false) Date startTime,
                           @ApiParam(value = "结束时间", name = "endTime") @RequestParam(value = "endTime", required = false) Date endTime) {
        RespPageBean page = computingLogService.getPage(pageNum, pageSize, verificationNo, shipName, startTime, endTime, principal);
        return Message.ok(page);
    }

    @GetMapping("/get2")
    @ApiOperation("根据tankId获取容量表基本信息")
    public Message getTableInfo(@ApiParam(value = "岸罐主键id", name = "tankId") @RequestParam Integer tankId) {
        TableInfo tableInfo = tableService.getTableInfoByTankId(tankId);
        return Message.ok(tableInfo);
    }

    @GetMapping("/get3")
    @ApiOperation("获取损益")
    public Message getProfitAndLoss(@ApiParam(value = "公司id", name = "companyId") @RequestParam(value = "companyId", required = false) Integer companyId,
                                    @ApiParam(value = "类型", name = "type") @RequestParam(value = "type", required = false) Byte type,
                                    @ApiParam(value = "交货类型", name = "type2") @RequestParam(value = "type2", required = false) Byte type2,
                                    @ApiParam(value = "船名", name = "shipName") @RequestParam(value = "shipName", required = false) String shipName,
                                    @ApiParam(value = "委托人", name = "principal") @RequestParam(value = "principal", required = false) String principal,
                                    @ApiParam(value = "开始时间", name = "startTime") @RequestParam(value = "startTime", required = false) Date startTime,
                                    @ApiParam(value = "结束时间", name = "endTime") @RequestParam(value = "endTime", required = false) Date endTime) {
        List<ProfitAndLossVo> profitAndLossVos = computingLogService.getProfitAndLossByCompanyId(companyId, type, type2, shipName, startTime, endTime, principal);
        return Message.ok(profitAndLossVos);
    }

    @GetMapping("/get4")
    @ApiOperation("根据id获取计算记录")
    public Message getComputingLogById(@ApiParam(value = "计算记录基础信息主键id", name = "computingLogBaseId") @RequestParam Long computingLogBaseId) {
        ComputingLogVo computingLogVo = computingLogService.getComputingLogById(computingLogBaseId);
        return Message.ok(computingLogVo);
    }

    @PostMapping("/add")
    @ApiOperation("添加计算记录")
    @RequiresPermissions("computinglog:add")
    public Message add(@ApiParam(value = "计算记录基础信息实体", name = "computingLogBase") @RequestBody ComputingLogVo computingLogVo) {
        computingLogService.addComputingLog(computingLogVo);
        return Message.ok();
    }

    @PutMapping("/update/{computingLogBaseId}/{status}")
    @ApiOperation("作废计算记录")
    @RequiresPermissions("computinglog:add")
    public Message update(@ApiParam(value = "计算记录基础信息主键id", name = "computingLogBaseId") @PathVariable Long computingLogBaseId,
                          @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        computingLogService.update(computingLogBaseId, status);
        return Message.ok();
    }

    @GetMapping("/log")
    @ApiOperation("出单打印日志")
    public Message log(@ApiParam(value = "单据类型", name = "type") Byte type,
                       @ApiParam(value = "主键id", name = "id") String id) {
        computingLogService.log(type, id);
        return Message.ok();
    }
}
