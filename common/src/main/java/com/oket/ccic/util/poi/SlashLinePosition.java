package com.oket.ccic.util.poi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-07 10:56
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlashLinePosition {
    /**
     * 斜线起始点X轴坐标
     **/
    private int startX;

    /**
     * 斜线起始点Y轴坐标
     **/
    private int startY;

    /**
     * 斜线结束点X轴坐标
     **/
    private int endX;

    /**
     * 斜线结束点Y轴坐标
     **/
    private int endY;
}
