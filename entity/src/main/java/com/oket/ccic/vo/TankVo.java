package com.oket.ccic.vo;

import com.oket.ccic.entity.Tank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 岸罐前后端交互
 * @author: dzp
 * @create: 2021-08-05 08:33
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "岸罐前后端交互")
public class TankVo extends Tank {

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "罐区名称")
    private String tankFieldName;
}
