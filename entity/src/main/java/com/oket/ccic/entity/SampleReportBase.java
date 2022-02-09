package com.oket.ccic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @program: Layer
 * @description: 取样报告单基本信息表
 * @author: dzp
 * @create: 2021-08-21 15:15
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "取样报告单基本信息表")
public class SampleReportBase {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "检定编号")
    private String verificationNo;

    @ApiModelProperty(value = "船名")
    private String shipName;

    @ApiModelProperty(value = "检验地点")
    private String verificationAddress;

    @ApiModelProperty(value = "货物名称")
    private String oilName;

    @ApiModelProperty(value = "检验日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date verificationDate;

    @ApiModelProperty(value = "取样标准")
    private String samplingStandard;

    @ApiModelProperty(value = "1：天气正常 其余为其它天气情况")
    private Byte weather;

    @ApiModelProperty(value = "创建人")
    private Integer createrId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;

    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty(value = "委托人")
    private String principal;
}
