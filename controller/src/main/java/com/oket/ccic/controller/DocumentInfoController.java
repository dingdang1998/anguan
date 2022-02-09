package com.oket.ccic.controller;

import com.oket.ccic.entity.DocumentInfo;
import com.oket.ccic.service.DocumentInfoService;
import com.oket.ccic.util.base.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: Layer
 * @description: 单据信息维护控制层
 * @author: dzp
 * @create: 2021-09-02 09:21
 **/
@RestController
@RequestMapping(value = "/document")
@Api(tags = "单据信息维护控制层")
public class DocumentInfoController {

    @Autowired
    private DocumentInfoService documentInfoService;

    @PostMapping("/add")
    @ApiOperation("添加单据维护信息")
    @RequiresPermissions("document:maintain")
    public Message add(@ApiParam(value = "单据信息实体", name = "documentInfo") @RequestBody DocumentInfo documentInfo) {
        int result = documentInfoService.addDocumentInfo(documentInfo);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PostMapping("/add2")
    @ApiOperation("批量添加单据维护信息")
    @RequiresPermissions("document:maintain")
    public Message addDocumentInfos(@ApiParam(value = "单据信息实体集合", name = "documentInfo") @RequestBody List<DocumentInfo> documentInfos) {
        int result = documentInfoService.addDocumentInfos(documentInfos);
        if (result == documentInfos.size()) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get")
    @ApiOperation("查询单据维护信息")
    @RequiresPermissions("document:search")
    public Message get(@ApiParam(value = "单据信息主键id", name = "documentInfoId") @RequestParam(value = "companyId") Integer companyId) {
        Map<Byte, List<DocumentInfo>> result = documentInfoService.get(companyId);
        return Message.ok(result);
    }

    @PutMapping("/update")
    @ApiOperation("修改单据维护信息")
    @RequiresPermissions("document:maintain")
    public Message update(@ApiParam(value = "单据信息主键id", name = "documentInfoId") @RequestBody DocumentInfo documentInfo) {
        int result = documentInfoService.update(documentInfo);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @DeleteMapping("/delete/{documentInfoId}")
    @ApiOperation("删除单据维护信息")
    @RequiresPermissions("document:maintain")
    public Message delete(@ApiParam(value = "单据信息主键id", name = "documentInfoId") @PathVariable Long documentInfoId) {
        int result = documentInfoService.delete(documentInfoId);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
