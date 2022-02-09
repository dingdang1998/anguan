package com.oket.ccic.vo;

import com.oket.ccic.entity.SampleReportBase;
import com.oket.ccic.entity.SampleReportContent;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 取样报告单Vo
 * @author: dzp
 * @create: 2021-08-24 14:41
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "取样报告单Vo")
public class SampleReportVo extends SampleReportBase {

    private String companyName;

    private String username;

    private List<SampleReportContent> sampleReportContents;
}
