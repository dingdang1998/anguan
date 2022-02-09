package com.oket.ccic.vo;

import com.oket.ccic.entity.TestingTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description:计量器具实体类Vo
 * @author: dzp
 * @create: 2021-08-09 15:18
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计量器具实体类Vo")
public class TestingToolVo extends TestingTool {
    @ApiModelProperty(value = "公司名称")
    private String companyName;
}
