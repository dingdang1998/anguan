package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 分页查询参数
 * @author: dzp
 * @create: 2021-08-16 11:25
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页查询参数")
public class PageArgs {

    @ApiModelProperty(value = "第几页")
    Integer pageNum;

    @ApiModelProperty(value = "一页几条")
    Integer pageSize;
}
