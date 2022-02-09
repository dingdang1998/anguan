package com.oket.ccic.bo;

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
 * @description:
 * @author: dzp
 * @create: 2021-08-24 13:34
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "计算记录业务层对象")
public class ComputingLogBo {

    private ComputingLogBase computingLogBase;

    private List<ComputingLogContent> computingLogContents;
}
