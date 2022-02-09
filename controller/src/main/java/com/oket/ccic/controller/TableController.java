package com.oket.ccic.controller;


import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.service.TableService;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @program: oketmicroservice
 * @description: 容量表控制层
 * @author: dzp
 * @create: 2021-05-28 09:34
 **/
@RestController
@RequestMapping(value = "/table")
@Api(tags = "容量表控制层")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping("/get")
    @ApiOperation("根据输入高度，获取对应容量")
    @RequiresPermissions("table:getcapacity")
    public Message getCapacity(@ApiParam(value = "油罐id", name = "tankId", required = true) @RequestParam Integer tankId,
                               @ApiParam(value = "液位，单位m", name = "inputHeightStr", required = true) @RequestParam String inputHeightStr,
                               @ApiParam(value = "底水高度，单位m", name = "levelHeightStr", required = true) @RequestParam String levelHeightStr) {
        Map<String, Long> result = tableService.getCapacity(tankId, inputHeightStr, levelHeightStr);
        return Message.ok(result);
    }

    @GetMapping("/get2")
    @ApiOperation("分页查询容量表")
    @RequiresPermissions("table:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "公司表主键id", name = "companyId") @RequestParam(value = "companyId", required = false) Integer companyId,
                           @ApiParam(value = "罐区表主键id", name = "tankFieldId") @RequestParam(value = "tankFieldId", required = false) Integer tankFieldId) {
        RespPageBean page = tableService.getPage(pageNum, pageSize, companyId, tankFieldId);
        return Message.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加容量表")
    @RequiresPermissions("table:add")
    public Message importTableExcel(@ApiParam(value = "容量表基础信息实体类", name = "tableInfo") TableInfo tableInfo,
                                    @ApiParam(value = "容量表Excel", name = "file") MultipartFile file) {
        tableService.addTable(tableInfo, file);
        return Message.ok();
    }

    @PutMapping("/update")
    @ApiOperation("编辑容量表基本信息")
    @RequiresPermissions("table:update")
    public Message update(@ApiParam(value = "容量表基础信息实体类", name = "tableInfo") @RequestBody TableInfo tableInfo) {
        int result = tableService.updateTableInfo(tableInfo);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update2")
    @ApiOperation("修改容量表状态")
    @RequiresPermissions("table:updatestatus")
    public Message updateStatus(@ApiParam(value = "容量表基础信息实体类", name = "tableInfo") @RequestBody TableInfo tableInfo) {
        int result = tableService.updateTableStatus(tableInfo);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @DeleteMapping("/delete/{tableId}")
    @ApiOperation("修改容量表状态")
    @RequiresPermissions("table:updatestatus")
    public Message delete(@ApiParam(value = "容量表主键id", name = "tableId") @PathVariable Integer tableId) {
        tableService.deleteTable(tableId);
        return Message.ok();
    }

    @GetMapping("/export")
    @ApiOperation("导出容量表")
    @RequiresPermissions("table:export")
    public ResponseEntity<byte[]> export(@ApiParam(value = "容量表基础信息实体类", name = "tableInfo") TableInfo tableInfo) {
        return tableService.export(tableInfo);
    }
}
