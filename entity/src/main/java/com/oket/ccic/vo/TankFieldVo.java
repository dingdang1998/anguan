package com.oket.ccic.vo;

import com.oket.ccic.entity.TankField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 罐区实体类Vo
 * @author: dzp
 * @create: 2021-08-03 14:59
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "罐区实体类Vo")
public class TankFieldVo extends TankField {

    @ApiModelProperty(value = "公司名称")
    private String companyName;
}
