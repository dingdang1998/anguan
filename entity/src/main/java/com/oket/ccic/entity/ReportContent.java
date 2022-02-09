package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 工作记录单内容表
 * @author: dzp
 * @create: 2021-08-21 15:06
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工作记录单内容表")
public class ReportContent {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "工作记录单基本表主键id")
    private Long reportBaseId;

    @ApiModelProperty(value = "岸罐编号")
    private String tankNo;

    @ApiModelProperty(value = "容量表检定编号")
    private String tableVerificationNo;

    @ApiModelProperty(value = "测深器具编号")
    private String soundToolNo;

    @ApiModelProperty(value = "测深器具检定编号")
    private String soundToolVerificationNo;

    @ApiModelProperty(value = "测温器具编号")
    private String thermometryToolNo;

    @ApiModelProperty(value = "测温器具检定编号")
    private String thermometryToolVerificationNo;
}
