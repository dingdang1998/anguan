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
 * @description: 容量表明细表实体类
 * @author: dzp
 * @create: 2021-05-27 08:47
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "容量表明细表实体类")
public class TableDetail {
    /**
     * 分米容量表
     */
    public static final int DM_TABLE = 1;
    /**
     * 厘米容量表
     */
    public static final int CM_TABLE = 2;
    /**
     * 毫米容量表
     */
    public static final int MM_TABLE = 3;
    /**
     * 罐底容量表
     */
    public static final int BOTTOM_TABLE = 4;
    /**
     * 静压力容量表
     */
    public static final int STATIC_PRESSURE = 5;
    /**
     * 起始高度结束高度为0.00
     */
    public static final String ZERO_START_END_HEIGHT = "0.00";

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "容量表基础信息表主键id")
    private Integer tableInfoId;

    @ApiModelProperty(value = "查询高度--对应容量")
    private BigDecimal height;

    @ApiModelProperty(value = "容量 单位L")
    private Long capacity;

    @ApiModelProperty(value = "容量表类型 1-分米 2-厘米 3-毫米 4-罐底 5-静压力")
    private Integer type;

    @ApiModelProperty(value = "起始高度")
    private BigDecimal startHeight;

    @ApiModelProperty(value = "终止高度")
    private BigDecimal endHeight;

    public TableDetail(Integer tableInfoId, BigDecimal height, Long capacity, Integer type, BigDecimal startHeight, BigDecimal endHeight) {
        this.tableInfoId = tableInfoId;
        this.height = height;
        this.capacity = capacity;
        this.type = type;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
    }
}
