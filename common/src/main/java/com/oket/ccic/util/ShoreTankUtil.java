package com.oket.ccic.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @program: oketmicroservice
 * @description: 岸罐使用的工具类
 * @author: dzp
 * @create: 2021-05-27 17:16
 **/
public class ShoreTankUtil {
    /**
     * 获取输入高度的分米段
     * 输入高度单位为m，小数点向右移一位，转换为dm，再将整数部分取出来
     *
     * @param inputHeight
     * @return
     */
    public static BigDecimal getInputHeightDm(BigDecimal inputHeight) {
        return inputHeight.movePointRight(1).setScale(0, RoundingMode.DOWN);
    }

    /**
     * 获取输入高度的厘米段
     * 小数点向右移三位，取十位数字
     *
     * @param inputHeight
     * @return
     */
    public static BigDecimal getInputHeightCm(BigDecimal inputHeight) {
        int i = inputHeight.movePointRight(3).intValue();
        return new BigDecimal((i / 10) % 10);
    }

    /**
     * 获取输入高度的毫米段
     * 小数点向右移三位，取个位数字
     *
     * @param inputHeight
     * @return
     */
    public static BigDecimal getInputHeightMm(BigDecimal inputHeight) {
        int i = inputHeight.movePointRight(3).intValue();
        return new BigDecimal(i % 10);
    }

    /**
     * 获取输入高度的小数点后两位
     *
     * @param inputHeightStr
     * @return
     */
    public static String getInputHeightStrLastTwo(String inputHeightStr) {
        return inputHeightStr.substring(inputHeightStr.indexOf(".") + 1);
    }
}
