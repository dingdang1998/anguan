package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 罐区实体类
 * @author: dzp
 * @create: 2021-08-03 14:59
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "罐区实体类")
public class TankField {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "罐区名称")
    private String tankFieldName;

    @ApiModelProperty(value = "罐区地址")
    private String tankFieldAddress;

    @ApiModelProperty(value = "罐区负责人")
    private String tankFieldPrincipal;

    @ApiModelProperty(value = "罐区电话")
    private String tankFieldPhone;

    @ApiModelProperty(value = "状态--0：禁用 1：启用")
    private Byte status;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;
}
