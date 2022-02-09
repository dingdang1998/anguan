package com.oket.ccic.vo;

import com.oket.ccic.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 用户Vo
 * @author: dzp
 * @create: 2021-08-12 13:46
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类Vo")
public class UserVo extends User {

    @ApiModelProperty(value = "角色表主键id")
    private Integer roleId;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;
}
