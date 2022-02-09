package com.oket.ccic.vo;

import com.oket.ccic.entity.ComputingLogBase;
import com.oket.ccic.entity.ComputingLogContent;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 计算记录前端交互
 * @author: dzp
 * @create: 2021-08-24 09:57
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算记录前端交互")
public class ComputingLogVo extends ComputingLogBase {
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 创建人--用户名
     */
    private String username;
    /**
     * 计算记录内容集合
     */
    private List<ComputingLogContent> computingLogContents;
}
