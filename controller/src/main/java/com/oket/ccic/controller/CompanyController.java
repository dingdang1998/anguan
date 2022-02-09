package com.oket.ccic.controller;

import com.oket.ccic.entity.Company;
import com.oket.ccic.service.CompanyService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.CompanyPageArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: Layer
 * @description: 公司控制层
 * @author: dzp
 * @create: 2021-08-03 15:59
 **/
@RestController
@RequestMapping(value = "/company")
@Api(tags = "公司控制层")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ShiroUtils shiroUtils;

    @GetMapping("/get")
    @ApiOperation("分页查询公司")
    @RequiresPermissions("company:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "公司名称", name = "companyName") @RequestParam(value = "companyName", required = false) String companyName) {
        RespPageBean page = companyService.getPage(new CompanyPageArgs(pageNum, pageSize, companyName, shiroUtils.getCompanyId()));
        return Message.ok(page);
    }

    @PostMapping("/add")
    @ApiOperation("添加公司")
    @RequiresPermissions("company:add")
    public Message add(@ApiParam(value = "公司实体", name = "company") @RequestBody Company company) {
        int result = companyService.addCompany(company);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update")
    @ApiOperation("编辑公司")
    @RequiresPermissions("company:update")
    public Message update(@ApiParam(value = "公司实体", name = "company") @RequestBody Company company) {
        int result = companyService.updateCompany(company);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PutMapping("/update/{companyId}/{status}")
    @ApiOperation("修改公司状态")
    @RequiresPermissions("company:updatestatus")
    public Message updateStatus(@ApiParam(value = "公司表主键id", name = "companyId") @PathVariable Integer companyId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = companyService.updateCompanyStatus(companyId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }
}
