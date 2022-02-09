package com.oket.ccic.vo;

import com.oket.ccic.entity.TableInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description:容量表基础信息实体类Vo
 * @author: dzp
 * @create: 2021-08-06 15:03
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "容量表基础信息实体类Vo")
public class TableInfoVo extends TableInfo {

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "罐区名称")
    private String tankFieldName;

    @ApiModelProperty(value = "岸罐编号")
    private String tankNo;
}
