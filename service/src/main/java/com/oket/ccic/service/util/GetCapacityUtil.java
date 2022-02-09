package com.oket.ccic.service.util;

import com.oket.ccic.entity.TableDetail;
import com.oket.ccic.util.ShoreTankUtil;
import com.oket.ccic.util.base.CustomException;
import com.oket.ccic.util.base.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: Layer
 * @description: 根据输入高度获取对应容量
 * @author: dzp
 * @create: 2021-09-03 10:11
 **/
public class GetCapacityUtil {

    private static final Logger logger = LoggerFactory.getLogger(GetCapacityUtil.class);

    /**
     * 输入高度最后两位为00
     */
    public static final String LAST_TWO_ZERO = "00";
    /**
     * 厘米表体积
     */
    public static final String CM = "cm";
    /**
     * 毫米表体积
     */
    public static final String MM = "mm";
    /**
     * 分米表体积
     */
    public static final String DM = "dm";
    /**
     * 静压力表体积
     */
    public static final String STATIC = "static";
    /**
     * 管底表体积
     */
    public static final String BOTTOM = "bottom";
    /**
     * 查表体积
     */
    public static final String CAPACITY_RESULT = "capacity_result";

    /**
     * 在分米表中获取对应容量
     *
     * @param inputHeightDm
     * @param tableDetailList
     * @return
     */
    public static long getCapacityDm(BigDecimal inputHeightDm, List<TableDetail> tableDetailList) {
        for (TableDetail tableDetail : tableDetailList) {
            if (inputHeightDm.compareTo(tableDetail.getHeight()) == 0) {
                logger.info("***" + inputHeightDm + "在分米表中的容量:" + tableDetail.getCapacity());
                return tableDetail.getCapacity();
            }
        }
        //防止意外情况，正常情况下不会出现
        throw new CustomException(Message.error(inputHeightDm.toString() + "在分米表中没有对应容量"));
    }

    /**
     * 获取输入高度在厘米毫米表中的容量
     * 1、将厘米毫米列表排序
     * 2、如果输入高度小于某一条的endHeight，那么就在这一区间内找对应的厘米毫米
     *
     * @param inputHeight
     * @param inputHeightCm
     * @param inputHeightMm
     * @param tableDetailListCm
     * @param tableDetailListMm
     * @return
     */
    public static Map<String, Long> getCapacityCmMm(BigDecimal inputHeight, BigDecimal inputHeightCm, BigDecimal inputHeightMm, List<TableDetail> tableDetailListCm, List<TableDetail> tableDetailListMm) {
        Map<String, Long> result = new HashMap<>();
        int cm = inputHeightCm.intValue();
        int mm = inputHeightMm.intValue();
        //如果厘米和毫米位都是0，直接返回
        if (cm == 0 && mm == 0) {
            result.put(CM, 0L);
            result.put(MM, 0L);
            return result;
        }
        //检查厘米位和毫米位是否为0
        boolean cmIsZero = false;
        boolean mmIsZero = false;
        if (cm == 0) {
            cmIsZero = true;
        }
        if (mm == 0) {
            mmIsZero = true;
        }
        //按终止高度排序
        List<TableDetail> collectCm = tableDetailListCm.stream().sorted(Comparator.comparing(TableDetail::getEndHeight)).collect(Collectors.toList());
        List<TableDetail> collectMm = tableDetailListMm.stream().sorted(Comparator.comparing(TableDetail::getEndHeight)).collect(Collectors.toList());
        //输入高度超过最大高度，防止意外情况，正常情况下不会出现
        BigDecimal endHeightMax = collectCm.get(collectCm.size() - 1).getEndHeight();
        if (inputHeight.compareTo(endHeightMax) >= 0) {
            throw new CustomException(Message.error("输入高度超过厘米毫米表最大高度"));
        }

        long cmCapacity = 0;
        long mmCapacity = 0;
        int indexStart = 0;
        List<TableDetail> endHeightCmList = new ArrayList<>();
        List<TableDetail> endHeightMmList = new ArrayList<>();
        BigDecimal endHeight = new BigDecimal("0.000");
        for (TableDetail tableDetail : collectCm) {
            if (inputHeight.compareTo(tableDetail.getEndHeight()) < 0) {
                endHeight = tableDetail.getEndHeight();
                indexStart = collectCm.indexOf(tableDetail);
                break;
            }
        }
        //把所有endHeight一样的找出来
        for (int i = indexStart; i < collectCm.size(); i++) {
            if (collectCm.get(i).getEndHeight().compareTo(endHeight) == 0) {
                endHeightCmList.add(collectCm.get(i));
                endHeightMmList.add(collectMm.get(i));
            } else {
                break;
            }
        }
        //找到厘米对应的容量
        if (!cmIsZero) {
            cmCapacity = endHeightCmList.get(inputHeightCm.subtract(new BigDecimal("1")).intValue()).getCapacity();
        }
        //找毫米表对应位置的容量
        if (!mmIsZero) {
            mmCapacity = endHeightMmList.get(inputHeightMm.subtract(new BigDecimal("1")).intValue()).getCapacity();
        }
        logger.info("***" + inputHeight + "在厘米表中的容量:" + cmCapacity + "--在毫米表中的容量:" + mmCapacity);
        result.put(CM, cmCapacity);
        result.put(MM, mmCapacity);
        return result;
    }

    /**
     * 获取输入高度在静压力表中的对应容量
     *
     * @param inputHeight
     * @param tableDetailList
     * @return
     */
    public static long getCapacityStaticPressure(BigDecimal inputHeight, List<TableDetail> tableDetailList) {
        //转化为分米高度
        String inputHeightDmStr = inputHeight.movePointRight(1).toString();
        //将静压力表按照高度排序
        List<TableDetail> collect = tableDetailList.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
        //获取最大高度
        BigDecimal heightMax = collect.get(collect.size() - 1).getHeight();
        //输入高度超过静压力表最大高度，防止意外情况，正常情况下不会出现
        if (inputHeight.compareTo(heightMax) > 0) {
            throw new CustomException(Message.error("输入高度值超过静压力表最大高度"));
        }
        //如果分米高度为整数，直接去找输入高度对应的容量
        String inputHeightStrLastTwo = ShoreTankUtil.getInputHeightStrLastTwo(inputHeightDmStr);
        if (LAST_TWO_ZERO.equals(inputHeightStrLastTwo)) {
            for (TableDetail tableDetail : collect) {
                if (inputHeight.compareTo(tableDetail.getHeight()) == 0) {
                    logger.info("***" + inputHeight + "在静压力表中的容量:" + tableDetail.getCapacity());
                    return tableDetail.getCapacity();
                }
            }
        }
        //插值法
        double beforeHeight = 0.00;
        double beforeCapacity = 0.00;
        double afterHeight = 0.00;
        double afterCapacity = 0.00;
        int index = 0;
        for (TableDetail tableDetail : collect) {
            if (inputHeight.compareTo(tableDetail.getHeight()) < 0) {
                //记录当前的高度和体积
                afterHeight = tableDetail.getHeight().doubleValue();
                afterCapacity = tableDetail.getCapacity();
                //记录当前元素位置
                index = collect.indexOf(tableDetail);
                break;
            }
        }
        //找前一条的高度和体积
        TableDetail tableDetail = collect.get(index - 1);
        beforeHeight = tableDetail.getHeight().doubleValue();
        beforeCapacity = tableDetail.getCapacity();
        //插值法求输入高度对应的静压力容量
        //插值法公式【(输入高度-前高度)/(后高度-输入高度) = (输入高度对应体积-前体积)/(输入高度对应体积-后体积)】
        double inputHeightDouble = inputHeight.doubleValue();
        double result = (((inputHeightDouble - beforeHeight) * afterCapacity) + ((afterHeight - inputHeightDouble) * beforeCapacity)) / (afterHeight - beforeHeight);
        logger.info("***" + inputHeight + "在静压力表中的前容量:" + beforeCapacity + "--在静压力表中的后容量:" + afterCapacity + "--插值法计算容量:" + Math.round(result));
        return Math.round(result);
    }

    /**
     * 输入高度在罐底表中对应的容量
     *
     * @param inputHeight
     * @param tableDetailList
     * @return
     */
    public static long getCapacityBottom(BigDecimal inputHeight, List<TableDetail> tableDetailList) {
        //转化成厘米
        inputHeight = inputHeight.movePointRight(2);
        for (TableDetail tableDetail : tableDetailList) {
            if (inputHeight.compareTo(tableDetail.getHeight()) == 0) {
                logger.info("***" + inputHeight + "在罐底表中的容量:" + tableDetail.getCapacity());
                return tableDetail.getCapacity();
            }
        }
        throw new CustomException(Message.error("在罐底表中没有对应高度"));
    }
}
