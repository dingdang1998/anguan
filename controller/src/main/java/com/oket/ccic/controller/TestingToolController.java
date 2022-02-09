package com.oket.ccic.controller;

import com.oket.ccic.entity.TestingTool;
import com.oket.ccic.service.TestingToolService;
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
 * @description: 计量器具控制层
 * @author: dzp
 * @create: 2021-08-09 14:37
 **/
@RestController
@RequestMapping(value = "/tool")
@Api(tags = "计量器具控制层")
public class TestingToolController {

    @Autowired
    private TestingToolService testingToolService;

    @PostMapping("/add")
    @ApiOperation("添加计量器具")
    @RequiresPermissions("testingtool:add")
    public Message add(@ApiParam(value = "计量器具实体", name = "testingTool") @RequestBody TestingTool testingTool) {
        int result = testingToolService.addTestingTool(testingTool);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get")
    @ApiOperation("分页查询计量器具")
    @RequiresPermissions("testingtool:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "公司表主键id", name = "companyId") @RequestParam(value = "companyId", required = false) Integer companyId) {
        RespPageBean page = testingToolService.getPage(pageNum, pageSize, companyId);
        return Message.ok(page);
    }

    @PutMapping("/update")
    @ApiOperation("编辑计量器具")
    @RequiresPermissions("testingtool:update")
    public Message update(@ApiParam(value = "计量器具实体", name = "testingTool") @RequestBody TestingTool testingTool) {
        int result = testingToolService.updateTestingTool(testingTool);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update/{toolId}/{status}")
    @ApiOperation("修改计量器具状态")
    @RequiresPermissions("testingtool:updatestatus")
    public Message updateStatus(@ApiParam(value = "计量工具表主键id", name = "toolId") @PathVariable Integer toolId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = testingToolService.updateTestingToolStatus(toolId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
