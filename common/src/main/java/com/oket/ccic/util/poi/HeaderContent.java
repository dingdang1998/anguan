package com.oket.ccic.util.poi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @program: Layer
 * @description: 表头内容实体类
 * @author: dzp
 * @create: 2021-07-12 11:45
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderContent {
    List<String[]> headerStrList;
    List<String[]> headerNumList;
}
