package com.oket.ccic.bo;

import com.oket.ccic.entity.Permission;
import com.oket.ccic.vo.UserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @program: oketmicroservice
 * @description: 用户信息业务层对象(包含当前用户的所有信息, 前端需要啥, 在这个实体里面加)
 * @author: dzp
 * @create: 2021-05-26 09:48
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户信息业务层对象")
public class UserInfo {

    @ApiModelProperty(value = "当前登录的用户")
    private UserVo userVo;

    @ApiModelProperty(value = "菜单--权限对应")
    private Map<String, List<Permission>> menuPermission;

    @ApiModelProperty(value = "游客公司id")
    private Integer visitorCompanyId;
}
