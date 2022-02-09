package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @program: Layer
 * @description: 计算记录内容实体
 * @author: dzp
 * @create: 2021-08-23 16:44
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算记录内容实体")
public class ComputingLogContent {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "计算记录基本信息表主键id")
    private Long computingLogBaseId;

    @ApiModelProperty(value = "岸罐表主键id")
    private Integer tankId;

    @ApiModelProperty(value = "岸罐编号")
    private String tankNo;

    @ApiModelProperty(value = "参高")
    private BigDecimal tankHeight;

    @ApiModelProperty(value = "岸罐类型 0：不保温 1：保温")
    private Byte tankType;

    @ApiModelProperty(value = "是否量油尺修正 0-否 1-是")
    private Byte isCorrection;

    @ApiModelProperty(value = "计算用浮顶质量首次 单位：吨")
    private BigDecimal computingFloaterWeightFirst;

    @ApiModelProperty(value = "计算用表载静压力密度")
    private BigDecimal staticPressureDensity;

    @ApiModelProperty(value = "首次密度")
    private BigDecimal densityFirst;

    @ApiModelProperty(value = "末次密度")
    private BigDecimal densityLast;

    @ApiModelProperty(value = "首次液位")
    private BigDecimal levelFirst;

    @ApiModelProperty(value = "末次液位")
    private BigDecimal levelLast;

    @ApiModelProperty(value = "首次液温")
    private BigDecimal liquidTemperatureFirst;

    @ApiModelProperty(value = "末次液温")
    private BigDecimal liquidTemperatureLast;

    @ApiModelProperty(value = "首次底水高度")
    private BigDecimal lowLevelHeightFirst;

    @ApiModelProperty(value = "末次底水高度")
    private BigDecimal lowLevelHeightLast;

    @ApiModelProperty(value = "首次底水体积")
    private BigDecimal lowLevelVolumeFirst;

    @ApiModelProperty(value = "末次底水体积")
    private BigDecimal lowLevelVolumeLast;

    @ApiModelProperty(value = "首次vcf")
    private BigDecimal vcfFirst;

    @ApiModelProperty(value = "末次vcf")
    private BigDecimal vcfLast;

    @ApiModelProperty(value = "首次水份")
    private BigDecimal waterFirst;

    @ApiModelProperty(value = "末次水份")
    private BigDecimal waterLast;

    @ApiModelProperty(value = "首次杂质")
    private BigDecimal impurityFirst;

    @ApiModelProperty(value = "末次杂质")
    private BigDecimal impurityLast;

    @ApiModelProperty(value = "首次环境温度")
    private String environmentTemperatureFirst;

    @ApiModelProperty(value = "末次环境温度")
    private String environmentTemperatureLast;

    @ApiModelProperty(value = "首次管线体积")
    private BigDecimal pipelineVolumeFirst;

    @ApiModelProperty(value = "末次管线体积")
    private BigDecimal pipelineVolumeLast;

    @ApiModelProperty(value = "首次日期")
    private java.sql.Date dateFirst;

    @ApiModelProperty(value = "末次日期")
    private java.sql.Date dateLast;

    @ApiModelProperty(value = "首次时间")
    private java.sql.Time timeFirst;

    @ApiModelProperty(value = "末次时间")
    private java.sql.Time timeLast;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检验日期")
    private String verificationDate;

    @ApiModelProperty(value = "计算用浮顶质量末次 单位：吨")
    private BigDecimal computingFloaterWeightLast;

    @Override
    public String toString() {
        return "ComputingLogContent{" +
                "id=" + id +
                ", computingLogBaseId=" + computingLogBaseId +
                ", tankId=" + tankId +
                ", tankNo='" + tankNo + '\'' +
                ", tankHeight=" + tankHeight +
                ", tankType=" + tankType +
                ", isCorrection=" + isCorrection +
                ", computingFloaterWeightFirst=" + computingFloaterWeightFirst +
                ", staticPressureDensity=" + staticPressureDensity +
                ", densityFirst=" + densityFirst +
                ", densityLast=" + densityLast +
                ", levelFirst=" + levelFirst +
                ", levelLast=" + levelLast +
                ", liquidTemperatureFirst=" + liquidTemperatureFirst +
                ", liquidTemperatureLast=" + liquidTemperatureLast +
                ", lowLevelHeightFirst=" + lowLevelHeightFirst +
                ", lowLevelHeightLast=" + lowLevelHeightLast +
                ", lowLevelVolumeFirst=" + lowLevelVolumeFirst +
                ", lowLevelVolumeLast=" + lowLevelVolumeLast +
                ", vcfFirst=" + vcfFirst +
                ", vcfLast=" + vcfLast +
                ", waterFirst=" + waterFirst +
                ", waterLast=" + waterLast +
                ", impurityFirst=" + impurityFirst +
                ", impurityLast=" + impurityLast +
                ", environmentTemperatureFirst='" + environmentTemperatureFirst + '\'' +
                ", environmentTemperatureLast='" + environmentTemperatureLast + '\'' +
                ", pipelineVolumeFirst=" + pipelineVolumeFirst +
                ", pipelineVolumeLast=" + pipelineVolumeLast +
                ", dateFirst=" + dateFirst +
                ", dateLast=" + dateLast +
                ", timeFirst=" + timeFirst +
                ", timeLast=" + timeLast +
                ", remark='" + remark + '\'' +
                ", verificationDate='" + verificationDate + '\'' +
                ", computingFloaterWeightLast=" + computingFloaterWeightLast +
                '}';
    }
}
