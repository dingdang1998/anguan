package com.oket.ccic.vo;

import com.oket.ccic.entity.PageArgs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 公司分页查询参数
 * @author: dzp
 * @create: 2021-08-16 11:45
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "公司分页查询参数")
public class CompanyPageArgs extends PageArgs {

    @ApiModelProperty(value = "公司名称")
    String companyName;

    @ApiModelProperty(value = "公司表主键id")
    Integer companyId;

    public CompanyPageArgs(Integer pageNum, Integer pageSize, String companyName, Integer companyId) {
        super(pageNum, pageSize);
        this.companyName = companyName;
        this.companyId = companyId;
    }
}
