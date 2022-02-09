package com.oket.ccic.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @program: Layer
 * @description: 公告实体类
 * @author: dzp
 * @create: 2021-08-18 09:59
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "公告实体类")
public class Notice {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "公告内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态")
    private Byte status;
}
