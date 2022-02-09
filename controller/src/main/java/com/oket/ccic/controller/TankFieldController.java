package com.oket.ccic.controller;

import com.oket.ccic.entity.TankField;
import com.oket.ccic.service.TankFiledService;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: Layer
 * @description: 罐区控制层
 * @author: dzp
 * @create: 2021-08-04 15:46
 **/
@RestController
@RequestMapping(value = "/field")
@Api(tags = "罐区控制层")
public class TankFieldController {

    @Autowired
    private TankFiledService tankFiledService;

    @PostMapping("/add")
    @ApiOperation("添加罐区")
    @RequiresPermissions("tankfield:add")
    public Message add(@ApiParam(value = "罐区实体", name = "tankField") @RequestBody TankField tankField) {
        int result = tankFiledService.addTankField(tankField);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get")
    @ApiOperation("获取罐区信息")
    @RequiresPermissions("tankfield:search")
    public Message get(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(defaultValue = "1") Integer pageNum,
                       @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(defaultValue = "10") Integer pageSize,
                       @ApiParam(value = "罐区名称", name = "tankFieldName") @RequestParam(value = "tankFieldName", required = false) String tankFieldName) {
        RespPageBean respPageBean = tankFiledService.getPage(pageNum, pageSize, tankFieldName, null);
        return Message.ok(respPageBean);
    }

    @PutMapping("/update")
    @ApiOperation("编辑罐区")
    @RequiresPermissions("tankfield:update")
    public Message update(@ApiParam(value = "罐区实体", name = "tankField") @RequestBody TankField tankField) {
        int result = tankFiledService.updateTankField(tankField);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update/{tankFieldId}/{status}")
    @ApiOperation("修改罐区状态")
    @RequiresPermissions("tankfield:updatestatus")
    public Message updateStatus(@ApiParam(value = "罐区表主键", name = "companyId") @PathVariable Integer tankFieldId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = tankFiledService.updateTankFieldStatus(tankFieldId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
