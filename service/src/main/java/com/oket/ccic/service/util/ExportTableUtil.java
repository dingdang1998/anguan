package com.oket.ccic.service.util;

import com.oket.ccic.entity.TableDetail;
import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.util.CustomTimeUtils;
import com.oket.ccic.util.poi.HeaderContent;
import com.oket.ccic.util.poi.HeaderContentFactory;
import com.oket.ccic.util.poi.PoiUtil;
import com.oket.ccic.util.poi.SlashLinePosition;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: Layer
 * @description: 导出容量表
 * @author: dzp
 * @create: 2021-08-06 16:28
 **/
@Component
public class ExportTableUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportTableUtil.class);
    private final ApplicationContext context = new AnnotationConfigApplicationContext(HeaderContentFactory.class);

    /**
     * 导出容量表
     *
     * @param tableInfo
     * @param map
     * @return
     */
    public ResponseEntity<byte[]> exportTable(TableInfo tableInfo, Map<Integer, List<TableDetail>> map) {
        String excelName = tableInfo.getTankNo();
        //获取HSSFWorkbook对象
        HSSFWorkbook workbook = PoiUtil.getHssfWorkbook(excelName);

        //创建封面sheet
        HSSFSheet coverSheet = workbook.createSheet("封面");
        //设置封面内容
        setCoverContent(coverSheet, tableInfo);

        //创建容量表sheet
        HSSFSheet dmSheet = workbook.createSheet("容量表");
        setDmSheetContent(dmSheet, map.get(TableDetail.DM_TABLE));

        //创建分段表
        HSSFSheet cmMmSheet = workbook.createSheet("分段表");
        setCmMmSheetContent(cmMmSheet, map.get(TableDetail.CM_TABLE), map.get(TableDetail.MM_TABLE));

        //创建罐底表
        HSSFSheet bottomSheet = workbook.createSheet("罐底表");
        HeaderContent bsHeaderContent = (HeaderContent) this.context.getBean("bsHeaderContent");
        setBottomSheetContent(workbook, bottomSheet, map.get(TableDetail.BOTTOM_TABLE), bsHeaderContent);

        //创建静压力表
        HSSFSheet staticSheet = workbook.createSheet("静压力表");
        setStaticSheetContent(workbook, staticSheet, map.get(TableDetail.STATIC_PRESSURE), bsHeaderContent);

        try {
            return PoiUtil.toResponseEntity(workbook, excelName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 设置封面内容
     *
     * @param coverSheet
     * @param tableInfo
     */
    private void setCoverContent(HSSFSheet coverSheet, TableInfo tableInfo) {
        //证书编号
        HSSFRow row = coverSheet.createRow(0);
        row.createCell(0).setCellValue("证书编号：");
        row.createCell(1).setCellValue(tableInfo.getTableNo());

        //型号/规格
        HSSFRow row1 = coverSheet.createRow(1);
        row1.createCell(0).setCellValue("型号/规格：");
        row1.createCell(1).setCellValue(tableInfo.getTypeSpecification());
        row1.createCell(2).setCellValue("m³");

        //浮顶质量
        HSSFRow row2 = coverSheet.createRow(2);
        row2.createCell(0).setCellValue("浮顶质量：");
        row2.createCell(1).setCellValue(tableInfo.getFloaterWeight().toString());
        row2.createCell(2).setCellValue("kg");
        //计量盲区
        HSSFRow row3 = coverSheet.createRow(3);
        row3.createCell(0).setCellValue("计量盲区：");
        StringBuilder stringBuilder = new StringBuilder(tableInfo.getLoseHeightFirst().toString()).append("~").append(tableInfo.getLoseHeightLast().toString());
        row3.createCell(1).setCellValue(stringBuilder.toString());
        row3.createCell(2).setCellValue("m");
        //检定日期
        HSSFRow row4 = coverSheet.createRow(4);
        row4.createCell(0).setCellValue("检定日期：");
        row4.createCell(1).setCellValue(CustomTimeUtils.formateToDay(tableInfo.getVerificationDate()));
        //有效期至
        HSSFRow row5 = coverSheet.createRow(5);
        row5.createCell(0).setCellValue("有效期至：");
        row5.createCell(1).setCellValue(CustomTimeUtils.formateToDay(tableInfo.getEffectiveTime()));
        //表载静压力密度
        HSSFRow row6 = coverSheet.createRow(6);
        row6.createCell(0).setCellValue("表载静压力密度：");
        row6.createCell(1).setCellValue(tableInfo.getStaticPressureDensity().toString());
        row6.createCell(2).setCellValue("g/cm³");
        //调整列宽适应内容
        coverSheet.autoSizeColumn(0, true);
        coverSheet.autoSizeColumn(1, true);
        coverSheet.autoSizeColumn(2, true);
    }

    /**
     * 设置容量表sheet内容
     *
     * @param dmSheet
     * @param dmTableDetails
     */
    private void setDmSheetContent(HSSFSheet dmSheet, List<TableDetail> dmTableDetails) {
        //按高度排序
        List<TableDetail> collect = dmTableDetails.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
        HSSFRow row = dmSheet.createRow(0);
        int rowNum = 0;
        int cellNum = 0;

        for (TableDetail tableDetail : collect) {
            //高度
            HSSFCell cell = row.createCell(cellNum);
            cell.setCellValue(tableDetail.getHeight().toString());
            dmSheet.autoSizeColumn(cellNum, true);
            //高度对应的体积
            cellNum++;
            HSSFCell cell1 = row.createCell(cellNum);
            cell1.setCellValue(tableDetail.getCapacity().toString());
            dmSheet.autoSizeColumn(cellNum, true);

            cellNum++;
            //一行写满8个，换下一行
            if (cellNum == 8) {
                cellNum = 0;
                rowNum++;
                row = dmSheet.createRow(rowNum);
            }
        }
    }

    /**
     * 设置分段表内容
     *
     * @param cmMmSheet
     * @param cmTableDetails
     * @param mmTableDetails
     */
    public void setCmMmSheetContent(HSSFSheet cmMmSheet, List<TableDetail> cmTableDetails, List<TableDetail> mmTableDetails) {
        //处理cm数据  将cm数据按起始高度分组并转为treeMap按key排序 key--startHeight
        Map<BigDecimal, List<TableDetail>> cmMapByStartHeight = cmTableDetails.stream().collect(Collectors.groupingBy(TableDetail::getStartHeight));
        TreeMap<BigDecimal, List<TableDetail>> cmTreeMap = new TreeMap<>(cmMapByStartHeight);
        //将每个分段再按高度排序
        for (Map.Entry<BigDecimal, List<TableDetail>> cmEntry : cmTreeMap.entrySet()) {
            List<TableDetail> value = cmEntry.getValue();
            List<TableDetail> collect = value.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
            cmEntry.setValue(collect);
        }
        //处理mm数据 同上
        Map<BigDecimal, List<TableDetail>> mmMapByStartHeight = mmTableDetails.stream().collect(Collectors.groupingBy(TableDetail::getStartHeight));
        TreeMap<BigDecimal, List<TableDetail>> mmTreeMap = new TreeMap<>(mmMapByStartHeight);
        for (Map.Entry<BigDecimal, List<TableDetail>> mmEntry : mmTreeMap.entrySet()) {
            List<TableDetail> value = mmEntry.getValue();
            List<TableDetail> collect = value.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
            mmEntry.setValue(collect);
        }

        setCmContent(cmMmSheet, cmTreeMap);
        setMmContent(cmMmSheet, mmTreeMap);
    }

    /**
     * 设置cm内容
     *
     * @param cmMmSheet
     * @param cmTreeMap
     */
    private void setCmContent(HSSFSheet cmMmSheet, TreeMap<BigDecimal, List<TableDetail>> cmTreeMap) {
        int rowNum = 0;
        //第1行
        setCmMmRow1(cmMmSheet, rowNum);
        //第2行
        rowNum++;
        setCmMmRow2(cmMmSheet, rowNum);
        //第三行内容
        rowNum++;
        for (Map.Entry<BigDecimal, List<TableDetail>> cmEntry : cmTreeMap.entrySet()) {
            List<TableDetail> value = cmEntry.getValue();
            BigDecimal startHeight = value.get(0).getStartHeight();
            BigDecimal endHeight = value.get(0).getEndHeight();

            HSSFRow row1 = cmMmSheet.getRow(rowNum - 2);
            row1.getCell(1).setCellValue(startHeight.toString());
            row1.getCell(4).setCellValue(endHeight.toString());

            for (int i = 0; i < value.size(); i++) {
                HSSFRow row = cmMmSheet.createRow(rowNum);
                for (int j = 0; j < 6; j++) {
                    row.createCell(j);
                }
                row.getCell(0).setCellValue(i + 1);
                row.getCell(1).setCellValue(value.get(i).getCapacity());
                cmMmSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));

                rowNum++;
            }

            setCmMmRow1(cmMmSheet, rowNum);
            rowNum++;

            setCmMmRow2(cmMmSheet, rowNum);
            rowNum++;
        }

        //最后会多两行，将多出的两行删除，要先将最后两行合并的单元格去掉，再将内容去掉
        List<CellRangeAddress> mergedRegions = cmMmSheet.getMergedRegions();
        cmMmSheet.removeMergedRegion(mergedRegions.size() - 1);
        cmMmSheet.removeMergedRegion(mergedRegions.size() - 2);
        cmMmSheet.removeMergedRegion(mergedRegions.size() - 3);
        cmMmSheet.removeRow(cmMmSheet.getRow(rowNum - 1));
        cmMmSheet.removeRow(cmMmSheet.getRow(rowNum - 2));
    }

    /**
     * 设置mm内容
     *
     * @param cmMmSheet
     * @param mmTreeMap
     */
    private void setMmContent(HSSFSheet cmMmSheet, TreeMap<BigDecimal, List<TableDetail>> mmTreeMap) {
        int rowNum = 2;
        for (Map.Entry<BigDecimal, List<TableDetail>> mmEntry : mmTreeMap.entrySet()) {
            List<TableDetail> value = mmEntry.getValue();
            for (int i = 0; i < value.size(); i++) {
                HSSFRow row = cmMmSheet.getRow(rowNum);
                row.getCell(3).setCellValue(i + 1);
                row.getCell(4).setCellValue(value.get(i).getCapacity());
                cmMmSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 5));
                rowNum++;
            }
            rowNum = rowNum + 2;
        }
    }

    /**
     * 设置分段表中的 自~~m至~~m 行
     *
     * @param cmMmSheet
     * @param rowNum
     */
    private void setCmMmRow1(HSSFSheet cmMmSheet, int rowNum) {
        HSSFRow row = cmMmSheet.createRow(rowNum);
        for (int i = 0; i < 6; i++) {
            row.createCell(i);
        }
        row.getCell(0).setCellValue("自");
        row.getCell(2).setCellValue("m至");
        row.getCell(5).setCellValue("m");
        cmMmSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
    }

    /**
     * 设置分段表中的 cm-容量-mm-容量 行
     *
     * @param cmMmSheet
     * @param rowNum
     */
    private void setCmMmRow2(HSSFSheet cmMmSheet, int rowNum) {
        HSSFRow row = cmMmSheet.createRow(rowNum);
        for (int i = 0; i < 6; i++) {
            row.createCell(i);
        }
        row.getCell(0).setCellValue("cm");
        row.getCell(1).setCellValue("容量");
        row.getCell(3).setCellValue("mm");
        row.getCell(4).setCellValue("容量");

        cmMmSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));
        cmMmSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 5));
    }

    /**
     * 设置罐底表内容
     *
     * @param bottomSheet
     * @param bottomTableDetails
     */
    private void setBottomSheetContent(HSSFWorkbook hssfWorkbook, HSSFSheet bottomSheet, List<TableDetail> bottomTableDetails, HeaderContent bsHeaderContent) {
        //画罐底表头上的斜线
        List<SlashLinePosition> list = new ArrayList<>();
        list.add(new SlashLinePosition(0, 0, 1023, 255));
        PoiUtil.drawLineXls(bottomSheet, 0, 0, 1, 0, list);
        //mm为单元格右上
        HSSFCellStyle cellStyle00 = hssfWorkbook.createCellStyle();
        cellStyle00.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle00.setVerticalAlignment(VerticalAlignment.TOP);
        HSSFRow row0 = bottomSheet.createRow(0);
        HSSFCell cell00 = row0.createCell(0);
        cell00.setCellValue("mm");
        cell00.setCellStyle(cellStyle00);
        //cm为单元格的左下
        HSSFCellStyle cellStyle01 = hssfWorkbook.createCellStyle();
        cellStyle01.setAlignment(HorizontalAlignment.LEFT);
        cellStyle01.setVerticalAlignment(VerticalAlignment.BOTTOM);
        HSSFRow row1 = bottomSheet.createRow(1);
        HSSFCell cell01 = row1.createCell(0);
        cell01.setCellValue("cm");
        cell01.setCellStyle(cellStyle01);
        //拼接表头
        String[] headerStr = bsHeaderContent.getHeaderStrList().get(0);
        for (int i = 1; i < headerStr.length + 1; i++) {
            HSSFCell cell = row0.createCell(i);
            cell.setCellValue(headerStr[i - 1]);
            // 根据字段长度自动调整列的宽度
            bottomSheet.autoSizeColumn(i, true);
        }
        PoiUtil.merge(bottomSheet, bsHeaderContent.getHeaderNumList());
        //填充内容
        List<TableDetail> collect = bottomTableDetails.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
        HSSFRow row = bottomSheet.createRow(2);
        int rowNum = 2;
        int cellNum = 1;
        int rowFirstNum = 0;
        //每一行头上的数
        HSSFCell cell2 = row.createCell(0);
        cell2.setCellValue(rowFirstNum);

        for (TableDetail tableDetail : collect) {
            //体积
            HSSFCell cell1 = row.createCell(cellNum);
            cell1.setCellValue(tableDetail.getCapacity().toString());
            bottomSheet.autoSizeColumn(cellNum, true);
            cellNum++;

            //一行写满10个，换下一行
            if (cellNum == 11) {
                cellNum = 1;
                rowNum++;
                rowFirstNum++;
                row = bottomSheet.createRow(rowNum);
                //换行后，把头上第一个单元格的数填充上
                HSSFCell cell3 = row.createCell(0);
                cell3.setCellValue(rowFirstNum);
            }
        }
    }

    /**
     * 设置静压力表内容
     *
     * @param bottomSheet
     * @param staticTableDetails
     */
    private void setStaticSheetContent(HSSFWorkbook hssfWorkbook, HSSFSheet bottomSheet, List<TableDetail> staticTableDetails, HeaderContent bsHeaderContent) {
        //画静压力表头上的斜线
        List<SlashLinePosition> list = new ArrayList<>();
        list.add(new SlashLinePosition(0, 0, 1023, 255));
        PoiUtil.drawLineXls(bottomSheet, 0, 0, 1, 0, list);
        //dm为单元格右上
        HSSFCellStyle cellStyle00 = hssfWorkbook.createCellStyle();
        cellStyle00.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle00.setVerticalAlignment(VerticalAlignment.TOP);
        HSSFRow row0 = bottomSheet.createRow(0);
        HSSFCell cell00 = row0.createCell(0);
        cell00.setCellValue("dm");
        cell00.setCellStyle(cellStyle00);
        //m为单元格的左下
        HSSFCellStyle cellStyle01 = hssfWorkbook.createCellStyle();
        cellStyle01.setAlignment(HorizontalAlignment.LEFT);
        cellStyle01.setVerticalAlignment(VerticalAlignment.BOTTOM);
        HSSFRow row1 = bottomSheet.createRow(1);
        HSSFCell cell01 = row1.createCell(0);
        cell01.setCellValue("m");
        cell01.setCellStyle(cellStyle01);
        //拼接表头
        String[] headerStr = bsHeaderContent.getHeaderStrList().get(0);
        for (int i = 1; i < headerStr.length + 1; i++) {
            HSSFCell cell = row0.createCell(i);
            cell.setCellValue(headerStr[i - 1]);
            // 根据字段长度自动调整列的宽度
            bottomSheet.autoSizeColumn(i, true);
        }
        PoiUtil.merge(bottomSheet, bsHeaderContent.getHeaderNumList());
        //填充内容
        List<TableDetail> collect = staticTableDetails.stream().sorted(Comparator.comparing(TableDetail::getHeight)).collect(Collectors.toList());
        HSSFRow row = bottomSheet.createRow(2);
        int rowNum = 2;
        int cellNum = 1;
        int rowFirstNum = 0;
        //每一行头上的数
        HSSFCell cell2 = row.createCell(0);
        cell2.setCellValue(rowFirstNum);

        for (TableDetail tableDetail : collect) {
            //体积
            HSSFCell cell1 = row.createCell(cellNum);
            cell1.setCellValue(tableDetail.getCapacity().toString());
            bottomSheet.autoSizeColumn(cellNum, true);
            cellNum++;

            //一行写满10个，换下一行
            if (cellNum == 11) {
                cellNum = 1;
                rowNum++;
                rowFirstNum++;
                row = bottomSheet.createRow(rowNum);
                //换行后，把头上第一个单元格的数填充上
                HSSFCell cell3 = row.createCell(0);
                cell3.setCellValue(rowFirstNum);
            }
        }
    }
}
