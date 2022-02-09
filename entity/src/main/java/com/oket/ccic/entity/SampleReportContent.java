package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 取样报告单内容表
 * @author: dzp
 * @create: 2021-08-21 15:30
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "取样报告单内容表")
public class SampleReportContent {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "取样报告单基本信息表主键id")
    private Long sampleReportBaseId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "检验地点")
    private String source;

    @ApiModelProperty(value = "目的")
    private String goal;

    @ApiModelProperty(value = "数量")
    private String amount;

    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "样品类别")
    private String sampleCategory;

    @ApiModelProperty(value = "签封号")
    private String signNo;
}
