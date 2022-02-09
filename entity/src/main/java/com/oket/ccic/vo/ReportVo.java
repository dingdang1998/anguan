package com.oket.ccic.vo;

import com.oket.ccic.entity.ReportBase;
import com.oket.ccic.entity.ReportContent;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 工作记录单前端交互
 * @author: dzp
 * @create: 2021-08-24 16:26
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工作记录单前端交互")
public class ReportVo extends ReportBase {

    private String companyName;

    private String username;

    private List<ReportContent> reportContents;
}
