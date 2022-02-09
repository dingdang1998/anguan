package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 角色实体类
 * @author: dzp
 * @create: 2021-08-11 14:25
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "角色实体类")
public class Role {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "子角色id列表")
    private String childrenIds;
}
