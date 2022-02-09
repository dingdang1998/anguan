package com.oket.ccic.util.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 前后端交互分页数据实体类
 * @author: dzp
 * @create: 2021-08-03 16:33
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "前后端交互分页数据实体类")
public class RespPageBean {

    @ApiModelProperty(value = "查询总数")
    private Long total;

    @ApiModelProperty(value = "查询数据")
    private List<?> data;
}
