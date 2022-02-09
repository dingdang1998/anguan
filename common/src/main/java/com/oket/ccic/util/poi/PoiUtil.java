package com.oket.ccic.util.poi;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: Layer
 * @description: poi工具类
 * @author: dzp
 * @create: 2021-08-06 16:37
 **/
public class PoiUtil {
    /**
     * 获取HSSFWorkbook
     *
     * @param excelName
     * @return
     */
    public static HSSFWorkbook getHssfWorkbook(String excelName) {
        //1. 创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 创建文档摘要
        workbook.createInformationProperties();
        //3. 获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle(excelName);
        //文档作者
        summInfo.setAuthor("ccic");
        // 文档备注
        summInfo.setComments("本文档由ccic提供");
        return workbook;
    }

    /**
     * 转化
     *
     * @param hssfWorkbook
     * @param excelName
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> toResponseEntity(HSSFWorkbook hssfWorkbook, String excelName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(excelName + ".xls", "UTF-8"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        hssfWorkbook.write(baos);
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    /**
     * 单元格画斜线
     *
     * @param sheet
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @param slpList
     */
    public static void drawLineXls(HSSFSheet sheet, int row1, int col1, int row2, int col2, List<SlashLinePosition> slpList) {
        HSSFPatriarch hssfPatriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor hssfClientAnchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) col1, row1, (short) col2, row2);
        HSSFShapeGroup hssfShapes = hssfPatriarch.createGroup(hssfClientAnchor);
        float verticalPointsPerPixel = hssfClientAnchor.getAnchorHeightInPoints(sheet);
        EscherGraphics eg = new EscherGraphics(hssfShapes, sheet.getWorkbook(), Color.black, verticalPointsPerPixel);
        EscherGraphics2d eg2D = new EscherGraphics2d(eg);

        for (SlashLinePosition slp : slpList) {
            eg2D.drawLine(slp.getStartX(), slp.getStartY(), slp.getEndX(), slp.getEndY());
        }
    }


    /**
     * 合并单元格
     *
     * @param sheet
     * @param headerNumList
     */
    public static void merge(HSSFSheet sheet, List<String[]> headerNumList) {
        //动态合并单元格
        for (String[] headerNum : headerNumList) {
            for (int j = 0; j < headerNum.length; j++) {
                sheet.autoSizeColumn(j, true);
                String[] temp = headerNum[j].split(",");
                int startRow = Integer.parseInt(temp[0]);
                int overRow = Integer.parseInt(temp[1]);
                int startCol = Integer.parseInt(temp[2]);
                int overCol = Integer.parseInt(temp[3]);
                if (startRow == overRow && startCol == overCol) {
                    continue;
                }
                sheet.addMergedRegion(new CellRangeAddress(startRow, overRow, startCol, overCol));
            }
        }
    }
}
