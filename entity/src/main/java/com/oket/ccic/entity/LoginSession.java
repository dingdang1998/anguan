package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: Layer
 * @description: 登录用户信息
 * @author: dzp
 * @create: 2021-08-20 13:35
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录用户信息")
public class LoginSession implements Serializable {

    private static final long serialVersionUID = 1847417165637588522L;

    @ApiModelProperty(value = "登录用户sessionId")
    private Serializable sessionId;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @Override
    public String toString() {
        return "LoginSession{" +
                "sessionId=" + sessionId +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
