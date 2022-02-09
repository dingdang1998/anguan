package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 计量器具实体类
 * @author: dzp
 * @create: 2021-08-07 08:57
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计量器具实体类")
public class TestingTool {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "器具编号")
    private String toolNo;

    @ApiModelProperty(value = "检定编号")
    private String verificationNo;

    @ApiModelProperty(value = "器具类型-0：测深 1：测温")
    private Byte toolType;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;

    @ApiModelProperty(value = "状态-0：启用 1：禁用")
    private Byte status;
}
