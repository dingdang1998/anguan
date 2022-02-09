package com.oket.ccic.vo;

import com.oket.ccic.entity.Company;
import com.oket.ccic.entity.TankField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 与前端交互
 * @author: dzp
 * @create: 2021-08-03 16:45
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "与前端交互")
public class CompanyVo extends Company {
    List<TankField> tankFields;
}
