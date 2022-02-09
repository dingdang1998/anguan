package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 单据信息
 * @author: dzp
 * @create: 2021-09-02 09:16
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "单据信息实体")
public class DocumentInfo {

    @ApiModelProperty(value = "主键id", position = 1)
    private Long id;

    @ApiModelProperty(value = "内容", position = 2)
    private String content;

    @ApiModelProperty(value = "类型 0：打印单公司名称（中文） 1：打印单公司名称（英文） 2：内部授权编号编号 3：工作记录单-检验依据  4：实施日期", position = 3)
    private Byte type;

    @ApiModelProperty(value = "公司表主键id", position = 4)
    private Integer companyId;
}
