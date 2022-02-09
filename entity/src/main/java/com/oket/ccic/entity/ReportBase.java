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
 * @description: 工作记录单基础信息表
 * @author: dzp
 * @create: 2021-08-21 14:50
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工作记录单基础信息表")
public class ReportBase {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "船名")
    private String shipName;

    @ApiModelProperty(value = "货物名称")
    private String oilName;

    @ApiModelProperty(value = "检定编号")
    private String verificationNo;

    @ApiModelProperty(value = "委托人")
    private String principal;

    @ApiModelProperty(value = "检定地点")
    private String verificationAddress;

    @ApiModelProperty(value = "提单重量")
    private String billedWeight;

    @ApiModelProperty(value = "首次：工作人员、工作时间、天气情况")
    private String first;

    @ApiModelProperty(value = "末次：工作人员、工作时间、天气情况")
    private String last;

    @ApiModelProperty(value = "测深--1:测深尺 2：油水界面测定仪 3：控制仪表 4：其它")
    private Byte sounding;

    @ApiModelProperty(value = "测温--1:水银温度计 2：电子温度计 3：控制仪表 4：其它")
    private Byte thermometry;

    @ApiModelProperty(value = "检验依据ids")
    private String inspectionBasisIds;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Integer createrId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "公司表主键id")
    private Integer companyId;

    @ApiModelProperty(value = "计量基本信息表主键id")
    private Long computingLogBaseId;

    @ApiModelProperty(value = "状态--0：禁用 1：启用")
    private Byte status;

    @ApiModelProperty(value = "类型--0：收货 1：发货")
    private Byte type;
}
