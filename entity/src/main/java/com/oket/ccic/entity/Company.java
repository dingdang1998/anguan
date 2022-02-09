package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: oketmicroservice
 * @description: 公司实体类
 * @author: dzp
 * @create: 2021-05-26 11:30
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "公司实体类")
public class Company {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "公司固定电话")
    private String companyTel;

    @ApiModelProperty(value = "公司移动电话")
    private String companyMobileTel;

    @ApiModelProperty(value = "公司传真")
    private String companyFax;

    @ApiModelProperty(value = "公司电子邮件")
    private String companyEmail;

    @ApiModelProperty(value = "一个公司下的账号数量，默认10个")
    private Integer accountNum;

    @ApiModelProperty(value = "0:禁用 1:启用")
    private Byte status;
}
