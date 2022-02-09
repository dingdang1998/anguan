package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 权限实体类
 * @author: dzp
 * @create: 2021-08-11 14:15
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "权限实体类")
public class Permission {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "菜单编码,前端判断并展示菜单使用")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "权限的代码/通配符,对应代码中@RequiresPermissions的value")
    private String permissionCode;

    @ApiModelProperty(value = "权限的中文含义")
    private String permissionName;
}
