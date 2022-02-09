package com.oket.ccic.service.impl;


import com.oket.ccic.entity.TableDetail;
import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.mapper.TableMapper;
import com.oket.ccic.service.TableService;
import com.oket.ccic.service.util.ExcelToTableDetailUtil;
import com.oket.ccic.service.util.ExportTableUtil;
import com.oket.ccic.service.util.GetCapacityUtil;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.ShoreTankUtil;
import com.oket.ccic.util.base.CustomException;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.TableInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: oketmicroservice
 * @description: 容量表服务实现类
 * @author: dzp
 * @create: 2021-05-27 09:06
 **/
@Service
public class TableServiceImpl implements TableService {

    private static final Logger logger = LoggerFactory.getLogger(TableServiceImpl.class);

    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private ExportTableUtil exportTableUtil;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public Map<String, Long> getCapacity(Integer tankId, String inputHeightStr, String levelHeightStr) {

        BigDecimal inputHeight;

        if (inputHeightStr != null && !"".equals(inputHeightStr)) {
            logger.info("***输入的高度--" + inputHeightStr);
            inputHeight = new BigDecimal(inputHeightStr);
        } else {
            logger.info("***输入的高度--" + levelHeightStr);
            inputHeight = new BigDecimal(levelHeightStr);
        }

        TableInfo tableInfo = tableMapper.getTableInfoByTankId(tankId);
        if (tableInfo != null) {
            //最终结果返回
            Map<String, Long> result = new HashMap<>();
            //最大高度
            BigDecimal totalHeight = tableInfo.getTotalHeight();
            //底量高度
            BigDecimal baseAmountHeight = tableInfo.getBaseAmountHeight();
            //起始失效高度
            BigDecimal loseHeightFirst = tableInfo.getLoseHeightFirst();
            //结束失效高度
            BigDecimal loseHeightLast = tableInfo.getLoseHeightLast();
            //输入高度跟最大高度比较结果
            int compareTotal = inputHeight.compareTo(totalHeight);
            //输入高度跟底量高度比较结果
            int compareBaseAmount = inputHeight.compareTo(baseAmountHeight);
            //输入高度跟初始失效高度比较结果
            int compareLoseHeightFirst = inputHeight.compareTo(loseHeightFirst);
            //输入高度跟结束失效高度比较结果
            int compareLoseHeightLast = inputHeight.compareTo(loseHeightLast);

            //初始失效高度<=输入高度<=结束失效高度
            //液位高度需要比较盲区，底水高度不需要比较
            if (inputHeightStr != null && !"".equals(inputHeightStr)) {
                if (compareLoseHeightFirst >= 0 && compareLoseHeightLast <= 0) {
                    throw new CustomException(Message.error("输入高度为盲区"));
                }
            }

            //获取容量表明细
            Map<Integer, List<TableDetail>> tableDetailMap = getTableDetail(tableInfo.getId());

            //最小高度<输入高度<=最大高度
            if (compareTotal <= 0 && compareBaseAmount > 0) {
                //拿到输入高度的三段值
                BigDecimal inputHeightDm = ShoreTankUtil.getInputHeightDm(inputHeight);
                BigDecimal inputHeightCm = ShoreTankUtil.getInputHeightCm(inputHeight);
                BigDecimal inputHeightMm = ShoreTankUtil.getInputHeightMm(inputHeight);
                //分米表对应容量
                long capacityDm = GetCapacityUtil.getCapacityDm(inputHeightDm, tableDetailMap.get(TableDetail.DM_TABLE));
                //厘米毫米表对应容量
                Map<String, Long> capacityCmMmMap = GetCapacityUtil.getCapacityCmMm(inputHeight, inputHeightCm, inputHeightMm, tableDetailMap.get(TableDetail.CM_TABLE), tableDetailMap.get(TableDetail.MM_TABLE));
                Long cmCapacity = capacityCmMmMap.get(GetCapacityUtil.CM);
                Long mmCapacity = capacityCmMmMap.get(GetCapacityUtil.MM);
                //静压力表对应容量
                long capacityStaticPressure = GetCapacityUtil.getCapacityStaticPressure(inputHeight, tableDetailMap.get(TableDetail.STATIC_PRESSURE));
                result.putAll(capacityCmMmMap);
                result.put(GetCapacityUtil.DM, capacityDm);
                result.put(GetCapacityUtil.STATIC, capacityStaticPressure);
                result.put(GetCapacityUtil.CAPACITY_RESULT, capacityDm + cmCapacity + mmCapacity);
                return result;
            }
            //输入高度<=最小高度
            else if (compareBaseAmount <= 0) {
                //罐底表对应容量
                long capacityBottom = GetCapacityUtil.getCapacityBottom(inputHeight, tableDetailMap.get(TableDetail.BOTTOM_TABLE));
                //静压力表对应容量
                long capacityStaticPressure = GetCapacityUtil.getCapacityStaticPressure(inputHeight, tableDetailMap.get(TableDetail.STATIC_PRESSURE));
                result.put(GetCapacityUtil.BOTTOM, capacityBottom);
                result.put(GetCapacityUtil.STATIC, capacityStaticPressure);
                result.put(GetCapacityUtil.CAPACITY_RESULT, capacityBottom);
                return result;
            } else {
                //输入高度>最大高度
                throw new CustomException(Message.error("输入高度大于容量表最大高度"));
            }
        }
        throw new CustomException(Message.error("该罐没有容量表"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTable(TableInfo tableInfo, MultipartFile file) {
        tableInfo.setCreateUserId(shiroUtils.getLoginUserVo().getId());
        Date createTime = new Date();
        tableInfo.setCreateTime(createTime);
        //保存容量表基本信息
        tableMapper.addTableInfo(tableInfo);
        //将excel转为TableDetail
        List<TableDetail> tableDetails = ExcelToTableDetailUtil.excelToTableDetailList(tableInfo.getId(), file);
        //将分米表找出来
        List<TableDetail> dmTableDetails = tableDetails.stream().filter(tableDetail -> tableDetail.getType() == 1).collect(Collectors.toList());
        //找出总高度和底量高度
        Optional<BigDecimal> min = dmTableDetails.stream().map(TableDetail::getHeight).min(BigDecimal::compareTo);
        Optional<BigDecimal> max = dmTableDetails.stream().map(TableDetail::getHeight).max(BigDecimal::compareTo);
        if (min.isPresent()) {
            //取出来的单位是dm，转换为m存到基本信息表中
            BigDecimal baseAmountHeight = min.get().movePointLeft(1);
            BigDecimal totalHeight = max.get().movePointLeft(1);
            //保存
            tableMapper.updateTableInfoHeight(tableInfo.getId(), baseAmountHeight, totalHeight);
        }
        //保存容量表明细
        tableMapper.addTableDetails(tableDetails);
    }

    @Override
    public int updateTableInfo(TableInfo tableInfo) {
        return tableMapper.updateTableInfo(tableInfo);
    }

    @Override
    public int updateTableStatus(TableInfo tableInfo) {
        //禁用
        if (tableInfo.getStatus() == 0) {
            return tableMapper.updateTableStatus(tableInfo.getId(), tableInfo.getStatus());
        }
        //启用
        queryUseTable(tableInfo);
        return tableMapper.updateTableStatus(tableInfo.getId(), tableInfo.getStatus());
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, Integer tankFieldId) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        if (companyId == null) {
            companyId = shiroUtils.getCompanyId();
        }
        List<TableInfoVo> tableInfoVos = tableMapper.getPage(pageNum, pageSize, companyId, tankFieldId);
        Long pageTotal = tableMapper.getPageTotal(companyId, tankFieldId);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(tableInfoVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public ResponseEntity<byte[]> export(TableInfo tableInfo) {
        //获取明细
        Map<Integer, List<TableDetail>> map = getTableDetail(tableInfo.getId());
        //导出容量表
        return exportTableUtil.exportTable(tableInfo, map);
    }

    @Override
    public TableInfo getTableInfoByTankId(Integer tankId) {
        return tableMapper.getTableInfoByTankId(tankId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTable(Integer tableId) {
        //删除基本信息表
        tableMapper.deleteTableInfo(tableId);
        //删除容量表内容信息
        tableMapper.deleteTableDetail(tableId);
    }

    /**
     * 检查是否有已经启用的容量表
     *
     * @param tableInfo
     */
    private void queryUseTable(TableInfo tableInfo) {
        int useTable = tableMapper.queryUseTableByTankId(tableInfo.getTankId());
        if (useTable == 1) {
            throw new CustomException(Message.error("该罐已经存在在使用的容量表！"));
        } else if (useTable > 1) {
            throw new CustomException(Message.error("罐--罐表对应关系异常，请联系管理员！"));
        }
    }

    /**
     * 将分米、厘米、毫米、罐底、静压力表分组
     *
     * @param tableInfoId
     * @return
     */
    private Map<Integer, List<TableDetail>> getTableDetail(Integer tableInfoId) {
        List<TableDetail> tableDetailList = tableMapper.getTableDetailByTableInfoId(tableInfoId);
        if (tableDetailList != null && tableDetailList.size() > 0) {
            return tableDetailList.stream().collect(Collectors.groupingBy(TableDetail::getType));
        } else {
            throw new CustomException(Message.error("该罐没有对应的容量表"));
        }
    }
}
