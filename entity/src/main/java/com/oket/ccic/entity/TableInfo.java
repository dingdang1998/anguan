package com.oket.ccic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: oketmicroservice
 * @description: 容量表基础信息实体类
 * @author: dzp
 * @create: 2021-05-27 08:32
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "容量表基础信息实体类")
public class TableInfo {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "证书编号")
    private String tableNo;

    @ApiModelProperty(value = "型号/规格")
    private String typeSpecification;

    @ApiModelProperty(value = "检定日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date verificationDate;

    @ApiModelProperty(value = "有效日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectiveTime;

    @ApiModelProperty(value = "浮顶质量")
    private Integer floaterWeight;

    @ApiModelProperty(value = "表载静压力密度")
    private BigDecimal staticPressureDensity;

    @ApiModelProperty(value = "总高度,容量表最大高度")
    private BigDecimal totalHeight;

    @ApiModelProperty(value = "底量高度，容量表最小高度")
    private BigDecimal baseAmountHeight;

    @ApiModelProperty(value = "起始失效高度")
    private BigDecimal loseHeightFirst;

    @ApiModelProperty(value = "结束失效高度")
    private BigDecimal loseHeightLast;

    @ApiModelProperty(value = "岸罐表主键id")
    private Integer tankId;

    @ApiModelProperty(value = "岸罐编号")
    private String tankNo;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;

    @ApiModelProperty(value = "罐区表主键id")
    private Integer tankFieldId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "被谁创建")
    private Integer createUserId;

    @ApiModelProperty(value = "状态")
    private Byte status;
}
