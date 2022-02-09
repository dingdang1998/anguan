package com.oket.ccic.controller;

import com.oket.ccic.entity.Tank;
import com.oket.ccic.service.TankService;
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
 * @description: 岸罐控制层
 * @author: dzp
 * @create: 2021-08-04 17:37
 **/
@RestController
@RequestMapping(value = "/tank")
@Api(tags = "岸罐控制层")
public class TankController {

    @Autowired
    private TankService tankService;

    @PostMapping("/add")
    @ApiOperation("添加岸罐")
    @RequiresPermissions("tank:add")
    public Message add(@ApiParam(value = "岸罐实体", name = "tank") @RequestBody Tank tank) {
        int result = tankService.addTank(tank);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get")
    @ApiOperation("分页查询岸罐")
    @RequiresPermissions("tank:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "公司表主键id", name = "companyId") @RequestParam(value = "companyId", required = false) Integer companyId,
                           @ApiParam(value = "罐区表主键id", name = "tankFieldId") @RequestParam(value = "tankFieldId", required = false) Integer tankFieldId) {
        RespPageBean page = tankService.getPage(pageNum, pageSize, companyId, tankFieldId);
        return Message.ok(page);
    }

    @PutMapping("/update")
    @ApiOperation("编辑岸罐")
    @RequiresPermissions("tank:update")
    public Message update(@ApiParam(value = "岸罐实体", name = "tank") @RequestBody Tank tank) {
        int result = tankService.updateTank(tank);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update/{tankId}/{status}")
    @ApiOperation("修改岸罐状态")
    @RequiresPermissions("tank:updatestatus")
    public Message updateStatus(@ApiParam(value = "岸罐表主键id", name = "tankId") @PathVariable Integer tankId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = tankService.updateTankStatus(tankId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
