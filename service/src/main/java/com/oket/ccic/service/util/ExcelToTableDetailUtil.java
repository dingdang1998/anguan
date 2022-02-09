package com.oket.ccic.service.util;

import com.oket.ccic.entity.TableDetail;
import com.oket.ccic.util.base.CustomException;
import com.oket.ccic.util.base.Message;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: excel转实体
 * @author: dzp
 * @create: 2021-05-29 10:00
 **/
public class ExcelToTableDetailUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelToTableDetailUtil.class);
    /**
     * 2007--excel文件后缀
     */
    public static final String XLSX = "xlsx";
    /**
     * 2003--excel文件后缀
     */
    public static final String XLS = "xls";
    /**
     * 汉字--自
     */
    public static final String CHINA_ZI = "自";
    /**
     * 导入excel中sheet个数
     */
    public static final int SHEET_NUM = 5;
    /**
     * 0-9数字集合
     */
    private static final List<String> ZERO_TO_NINE = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    /**
     * 容量表Excel转容量表明细list
     *
     * @param tableInfoId
     * @param file
     * @return
     */
    public static List<TableDetail> excelToTableDetailList(Integer tableInfoId, MultipartFile file) {
        //获取 workbook 对象
        Workbook workbook = getReadWorkBookType(file);
        //获取 workbook 中表单的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //返回结果
        List<TableDetail> result = new ArrayList<>();

        if (numberOfSheets >= SHEET_NUM) {
            //容量表--excel中的容量表
            Sheet dmSheet = workbook.getSheetAt(1);
            if (dmSheet.getPhysicalNumberOfRows() > 0) {
                List<TableDetail> dmTableDetails = dmSheetToList(tableInfoId, dmSheet);
                if (dmTableDetails.size() > 0) {
                    result.addAll(dmTableDetails);
                } else {
                    throw new CustomException(Message.error("容量表解析失败，请检查"));
                }
            } else {
                throw new CustomException(Message.error("容量表Sheet为空，请检查"));
            }
            //分段表--excel中的分段表
            Sheet cmMmSheet = workbook.getSheetAt(2);
            if (cmMmSheet.getPhysicalNumberOfRows() > 0) {
                List<TableDetail> cmMmTableDetails = cmMmSheetToList(tableInfoId, cmMmSheet);
                if (cmMmTableDetails.size() > 0) {
                    result.addAll(cmMmTableDetails);
                } else {
                    throw new CustomException(Message.error("分段表解析失败，请检查"));
                }
            } else {
                throw new CustomException(Message.error("分段表Sheet为空，请检查"));
            }
            //罐底表
            Sheet bottomSheet = workbook.getSheetAt(3);
            if (bottomSheet.getPhysicalNumberOfRows() > 0) {
                List<TableDetail> bottomTableDetails = bottomSheetToList(tableInfoId, bottomSheet);
                if (bottomTableDetails.size() > 0) {
                    result.addAll(bottomTableDetails);
                } else {
                    throw new CustomException(Message.error("罐底表解析失败，请检查"));
                }
            } else {
                throw new CustomException(Message.error("罐底表Sheet为空，请检查"));
            }
            //静压力表
            Sheet staticPressureSheetAt = workbook.getSheetAt(4);
            if (staticPressureSheetAt.getPhysicalNumberOfRows() > 0) {
                List<TableDetail> staticPressureTableDetails = staticPressureSheetToList(tableInfoId, staticPressureSheetAt);
                if (staticPressureTableDetails.size() > 0) {
                    result.addAll(staticPressureTableDetails);
                } else {
                    throw new CustomException(Message.error("静压力表解析失败，请检查"));
                }
            } else {
                throw new CustomException(Message.error("静压力表Sheet为空，请检查"));
            }
            return result;
        }
        throw new CustomException(Message.error("导入的容量表sheet不足5个，请检查"));
    }

    /**
     * 根据上传的excel格式，返回相应的Workbook对象
     *
     * @param file
     * @return
     */
    public static Workbook getReadWorkBookType(MultipartFile file) {
        //获取上传文件名称
        String filename = file.getOriginalFilename();
        try {
            if (filename != null) {
                if (filename.toLowerCase().endsWith(XLS)) {
                    return new HSSFWorkbook(file.getInputStream());
                } else if (filename.toLowerCase().endsWith(XLSX)) {
                    return new XSSFWorkbook(file.getInputStream());
                } else {
                    throw new CustomException(Message.error("文件格式错误"));
                }
            }
        } catch (IOException e) {
            throw new CustomException(Message.error(e.getMessage()));
        }
        throw new CustomException(Message.error("excel文件名为空"));
    }

    /**
     * 容量表sheet读取转化
     *
     * @param tableInfoId
     * @param sheet
     * @return
     */
    private static List<TableDetail> dmSheetToList(Integer tableInfoId, Sheet sheet) {
        List<TableDetail> resultList = new ArrayList<>();
        //高度列表
        List<String> heightList = new ArrayList<>();
        //容量列表
        List<String> capacityList = new ArrayList<>();
        //获取sheet中的行数
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < physicalNumberOfRows; i++) {
            //获取行
            Row row = sheet.getRow(i);
            if (row == null) {
                //防止数据中间有空行
                continue;
            }
            //获取列数
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            //将高度和容量放到两个不同的list中
            for (int j = 0; j < physicalNumberOfCells; j++) {

                //将每个单元格的类型都设置为 字符串 形式
                try {
                    row.getCell(j).setCellType(CellType.STRING);
                } catch (NullPointerException e) {
                    continue;
                } catch (Exception e) {
                    throw new CustomException(Message.error("容量表第" + (i + 1) + "行" + "第" + (j + 1) + "列请检查"
                            + "；容量表读取了" + physicalNumberOfRows + "行" + physicalNumberOfCells + "列，请删除多余的空行空列"));
                }

                //不为空的单元格数据保存在list中
                String stringCellValue = row.getCell(j).getStringCellValue().replace(" ", "");
                if (!"".equals(stringCellValue)) {
                    if (j % 2 == 0) {
                        heightList.add(stringCellValue);
                    } else {
                        capacityList.add(stringCellValue);
                    }
                }

            }
        }
        //汇总数据，返回结果
        if (heightList.size() == capacityList.size()) {
            for (int i = 0; i < heightList.size(); i++) {
                try {
                    //转成TableDetail实体
                    TableDetail tableDetail = new TableDetail(
                            tableInfoId,
                            new BigDecimal(heightList.get(i)),
                            Math.round(Double.parseDouble(capacityList.get(i))),
                            TableDetail.DM_TABLE,
                            new BigDecimal(TableDetail.ZERO_START_END_HEIGHT),
                            new BigDecimal(TableDetail.ZERO_START_END_HEIGHT)
                    );
                    resultList.add(tableDetail);
                } catch (Exception e) {
                    //单元格中有可能有特殊字符出现
                    throw new CustomException(Message.error("容量表中存在特殊字符，请检查"));
                }
            }
        } else {
            //高度跟体积不对应
            throw new CustomException(Message.error("容量表高度跟体积不对应，请检查"));
        }
        logger.info(tableInfoId + "导入容量表数量:" + resultList.size() + "--" + new Date());
        return resultList;
    }

    /**
     * 导入罐底表
     *
     * @param tableInfoId
     * @param sheet
     * @return
     */
    private static List<TableDetail> bottomSheetToList(Integer tableInfoId, Sheet sheet) {
        //返回结果
        List<TableDetail> result = new ArrayList<>();
        //读取表单的行数
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows > 1) {
            for (int i = 0; i < physicalNumberOfRows; i++) {
                //获取行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //获取列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                if (physicalNumberOfCells > 1) {
                    //第一行
                    if (i == 0) {
                        //判断第一行是否为毫米行
                        try {
                            row.getCell(0).setCellType(CellType.STRING);
                        } catch (Exception e) {
                            throw new CustomException("罐底表第1行第一个单元格请检查");
                        }
                        if (!"mm".equals(row.getCell(0).getStringCellValue())) {
                            throw new CustomException(Message.error("罐底表没有mm，请检查"));
                        }
                    }
                    //第二行，判断第二行第一个cell是否为cm
                    else if (i == 1) {
                        //判断第一列是否为厘米列
                        try {
                            row.getCell(0).setCellType(CellType.STRING);
                        } catch (Exception e) {
                            throw new CustomException("罐底表第2行第一个单元格请检查");
                        }
                        if (!"cm".equals(row.getCell(0).getStringCellValue())) {
                            throw new CustomException(Message.error("罐底表没有cm，请检查"));
                        }
                    }
                    //第三行往后
                    else {
                        String cmStr = null;

                        for (int j = 0; j <= 10; j++) {

                            //将每个单元格的类型都设置为 字符串 形式
                            try {
                                row.getCell(j).setCellType(CellType.STRING);
                            } catch (NullPointerException ignored) {

                            }

                            if (j == 0) {
                                cmStr = row.getCell(j).getStringCellValue();
                            } else if (row.getCell(j) != null && !CellType.BLANK.equals(row.getCell(j).getCellTypeEnum()) && !"".equals(row.getCell(j).getStringCellValue().replace(" ", ""))) {
                                try {
                                    //计算height
                                    BigDecimal cmHeight = new BigDecimal(cmStr);//cm高度
                                    BigDecimal mmHeight = new BigDecimal(ZERO_TO_NINE.get(j - 1));//mm高度
                                    BigDecimal height = cmHeight.add(mmHeight.movePointLeft(1));//最终高度
                                    //封装罐底表TableDetail实体
                                    TableDetail tableDetail = new TableDetail(
                                            tableInfoId,
                                            height,
                                            Math.round(Double.parseDouble(row.getCell(j).getStringCellValue())),
                                            TableDetail.BOTTOM_TABLE,
                                            new BigDecimal(TableDetail.ZERO_START_END_HEIGHT),
                                            new BigDecimal(TableDetail.ZERO_START_END_HEIGHT));
                                    result.add(tableDetail);
                                } catch (Exception e) {
                                    throw new CustomException(Message.error("罐底中存在特殊字符，请检查"));
                                }
                            }
                        }

                    }
                }
            }
        }
        return result;
    }

    /**
     * 导入静压力表
     *
     * @param tableInfoId
     * @param sheet
     * @return
     */
    private static List<TableDetail> staticPressureSheetToList(Integer tableInfoId, Sheet sheet) {
        //返回结果
        List<TableDetail> result = new ArrayList<>();
        //读取表单的行数
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows > 1) {
            for (int i = 0; i < physicalNumberOfRows; i++) {
                //获取行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //获取列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                if (physicalNumberOfCells > 1) {
                    //第一行，判断第二行第一个cell是否为dm
                    if (i == 0) {
                        //判断第一行是否为分米行
                        try {
                            row.getCell(0).setCellType(CellType.STRING);
                        } catch (Exception e) {
                            throw new CustomException("静压力表第1行第一个单元格请检查");
                        }
                        if (!"dm".equals(row.getCell(0).getStringCellValue())) {
                            throw new CustomException(Message.error("静压力表没有dm，请检查"));
                        }
                    }
                    //第二行，判断第二行第一个cell是否为m
                    else if (i == 1) {
                        //判断第一列是否为厘米列
                        try {
                            row.getCell(0).setCellType(CellType.STRING);
                        } catch (Exception e) {
                            throw new CustomException("静压力表第2行第一个单元格请检查");
                        }
                        if (!"m".equals(row.getCell(0).getStringCellValue())) {
                            throw new CustomException(Message.error("静压力表没有m，请检查"));
                        }
                    }
                    //第三行往后
                    else {
                        String mStr = null;

                        for (int j = 0; j <= 10; j++) {

                            //将每个单元格的类型都设置为 字符串 形式
                            try {
                                row.getCell(j).setCellType(CellType.STRING);
                            } catch (NullPointerException ignored) {

                            }

                            if (j == 0) {
                                mStr = row.getCell(j).getStringCellValue();
                            } else {
                                //不为空计算并记录
                                if (row.getCell(j) != null && !CellType.BLANK.equals(row.getCell(j).getCellTypeEnum()) && !"".equals(row.getCell(j).getStringCellValue().replace(" ", ""))) {
                                    try {
                                        //计算height
                                        BigDecimal cmHeight = new BigDecimal(mStr);//m高度
                                        BigDecimal mmHeight = new BigDecimal(ZERO_TO_NINE.get(j - 1));//dm高度
                                        BigDecimal height = cmHeight.add(mmHeight.movePointLeft(1));//最终高度
                                        //封装罐底表TableDetail实体
                                        TableDetail tableDetail = new TableDetail(
                                                tableInfoId,
                                                height,
                                                Math.round(Double.parseDouble(row.getCell(j).getStringCellValue())),
                                                TableDetail.STATIC_PRESSURE,
                                                new BigDecimal(TableDetail.ZERO_START_END_HEIGHT),
                                                new BigDecimal(TableDetail.ZERO_START_END_HEIGHT));
                                        result.add(tableDetail);
                                    } catch (Exception e) {
                                        throw new CustomException(Message.error("静压力表中存在特殊字符，请检查"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 导入分段表
     *
     * @param tableInfoId
     * @param sheet
     * @return
     */
    private static List<TableDetail> cmMmSheetToList(Integer tableInfoId, Sheet sheet) {
        List<TableDetail> result = new ArrayList<>();
        //读取表单的行数
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows > 2) {
            //起始高度
            String startHeight = null;
            //终止高度
            String endHeight = null;
            for (int i = 0; i < physicalNumberOfRows; i++) {
                //获取行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //获取这一行的列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                try {
                    if (physicalNumberOfCells > 0) {
                        //拿到第一个单元格
                        try {
                            row.getCell(0).setCellType(CellType.STRING);
                        } catch (Exception e) {
                            throw new CustomException(Message.error("分段表第" + (i + 1) + "行第1列请检查"
                                    + "；分段表读取了" + physicalNumberOfRows + "行" + physicalNumberOfCells + "列，请删除多余的空行空列"));
                        }
                        String firstCellStr = row.getCell(0).getStringCellValue();
                        //第一个单元格是"自"
                        if (CHINA_ZI.equals(firstCellStr)) {
                            try {
                                row.getCell(1).setCellType(CellType.STRING);
                                row.getCell(4).setCellType(CellType.STRING);
                                startHeight = row.getCell(1).getStringCellValue();
                                endHeight = row.getCell(4).getStringCellValue();
                            } catch (Exception e) {
                                throw new CustomException(Message.error("分段表第" + (i + 1) + "行第2列或第5列请检查"
                                        + "；分段表读取了" + physicalNumberOfRows + "行" + physicalNumberOfCells + "列，请删除多余的空行空列"));
                            }
                        }
                        //第一个单元格是"cm"
                        else if ("cm".equals(firstCellStr)) {
                            continue;
                        }
                        //第一个单元格是1-9
                        else if (firstCellIsNumber(firstCellStr)) {
                            try {
                                //毫米高度
                                row.getCell(3).setCellType(CellType.STRING);
                                //厘米体积
                                row.getCell(1).setCellType(CellType.STRING);
                                //毫米体积
                                row.getCell(4).setCellType(CellType.STRING);
                            } catch (Exception e) {
                                throw new CustomException(Message.error("分段表第" + (i + 1) + "行数据请检查"
                                        + "；分段表读取了" + physicalNumberOfRows + "行" + physicalNumberOfCells + "列，请删除多余的空行空列"));
                            }
                            String mmHeight = row.getCell(3).getStringCellValue();
                            String cmCapacity = row.getCell(1).getStringCellValue();
                            String mmCapacity = row.getCell(4).getStringCellValue();
                            //厘米实体
                            TableDetail cmTableDetail = new TableDetail(tableInfoId,
                                    new BigDecimal(firstCellStr),
                                    Math.round(Double.parseDouble(cmCapacity)),
                                    TableDetail.CM_TABLE,
                                    new BigDecimal(startHeight),
                                    new BigDecimal(endHeight));
                            //毫米实体
                            TableDetail mmTableDetail = new TableDetail(tableInfoId,
                                    new BigDecimal(mmHeight),
                                    Math.round(Double.parseDouble(mmCapacity)),
                                    TableDetail.MM_TABLE,
                                    new BigDecimal(startHeight),
                                    new BigDecimal(endHeight));
                            result.add(cmTableDetail);
                            result.add(mmTableDetail);
                        }
                    }
                } catch (Exception e) {
                    throw new CustomException(Message.error("请检查分段表模板格式"
                            + "；分段表读取了" + physicalNumberOfRows + "行" + physicalNumberOfCells + "列，请删除多余的空行空列"));
                }
            }
        }
        return result;
    }

    /**
     * 分段表第一个单元格为1—9
     *
     * @param firstCellStr
     * @return
     */
    private static boolean firstCellIsNumber(String firstCellStr) {
        return "1".equals(firstCellStr) ||
                "2".equals(firstCellStr) ||
                "3".equals(firstCellStr) ||
                "4".equals(firstCellStr) ||
                "5".equals(firstCellStr) ||
                "6".equals(firstCellStr) ||
                "7".equals(firstCellStr) ||
                "8".equals(firstCellStr) ||
                "9".equals(firstCellStr);
    }
}
