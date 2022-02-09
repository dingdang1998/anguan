package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @program: oketmicroservice
 * @description: 岸罐实体类
 * @author: dzp
 * @create: 2021-05-27 08:27
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "岸罐实体类")
public class Tank {
    @ApiModelProperty(value = "油罐表主键id")
    private Integer id;

    @ApiModelProperty(value = "岸罐编号")
    private String tankNo;

    @ApiModelProperty(value = "参高")
    private BigDecimal height;

    @ApiModelProperty(value = "罐区表主键id")
    private Integer tankFieldId;

    @ApiModelProperty(value = "是否保温-0：不保温 1：保温")
    private Byte keepWarm;

    @ApiModelProperty(value = "状态-0：禁用 1：启用")
    private Byte status;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;
}
