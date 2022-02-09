package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: Layer
 * @description: 计算记录基础信息实体
 * @author: dzp
 * @create: 2021-08-23 16:31
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算记录基础信息实体")
public class ComputingLogBase {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "检定编号")
    private String verificationNo;

    @ApiModelProperty(value = "船名")
    private String shipName;

    @ApiModelProperty(value = "货物名称")
    private String oilName;

    @ApiModelProperty(value = "检验地点id")
    private String verificationAddressId;

    @ApiModelProperty(value = "检验地点name")
    private String verificationAddressName;

    @ApiModelProperty(value = "vcf计算方式")
    private String vcfMethod;

    @ApiModelProperty(value = "类型--0：收货 1：发货")
    private Byte type;

    @ApiModelProperty(value = "交货类型：0--船到罐 1--罐到罐")
    private Byte type2;

    @ApiModelProperty(value = "桶BBLS")
    private Integer bucket;

    @ApiModelProperty(value = "15℃立方米m³")
    private BigDecimal stere;

    @ApiModelProperty(value = "公吨")
    private BigDecimal metricTon;

    @ApiModelProperty(value = "长吨")
    private BigDecimal longTon;

    @ApiModelProperty(value = "损益_立方米")
    private BigDecimal palStere;

    @ApiModelProperty(value = "损益_公吨")
    private BigDecimal palMt;

    @ApiModelProperty(value = "立方米损益百分比（％）")
    private BigDecimal palSterePercentage;

    @ApiModelProperty(value = "公吨损益百分比（%）")
    private BigDecimal palMtPercentage;

    @ApiModelProperty(value = "创建人")
    private Integer createrId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty(value = "源id：根据哪条计算记录修改来的")
    private Long sourceId;

    @ApiModelProperty(value = "罐壁修正系数")
    private Byte correctionFactor;

    @ApiModelProperty(value = "委托人")
    private String principal;
}
