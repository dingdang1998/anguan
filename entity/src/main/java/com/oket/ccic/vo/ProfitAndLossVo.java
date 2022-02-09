package com.oket.ccic.vo;

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
 * @description: 首页曲线图损益Vo
 * @author: dzp
 * @create: 2021-09-24 17:07
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "首页曲线图损益Vo")
public class ProfitAndLossVo {

    @ApiModelProperty(value = "检定编号")
    private String verificationNo;

    @ApiModelProperty(value = "损益_立方米")
    private BigDecimal palStere;

    @ApiModelProperty(value = "损益_公吨")
    private BigDecimal palMt;

    @ApiModelProperty(value = "立方米损益百分比（％）")
    private BigDecimal palSterePercentage;

    @ApiModelProperty(value = "公吨损益百分比（%）")
    private BigDecimal palMtPercentage;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "船名")
    private String shipName;

    @ApiModelProperty(value = "委托人")
    private String principal;
}
