package com.oket.ccic.service;

import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.util.base.RespPageBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @program: oketmicroservice
 * @description: 容量表服务类
 * @author: dzp
 * @create: 2021-05-27 09:05
 **/
public interface TableService {
    /**
     * 获取容量
     *
     * @param tankId         油罐id
     * @param inputHeightStr 液位
     * @param levelHeightStr 底水高度
     * @return
     */
    Map<String, Long> getCapacity(Integer tankId, String inputHeightStr, String levelHeightStr);

    /**
     * 添加容量表
     *
     * @param tableInfo
     * @param file
     */
    void addTable(TableInfo tableInfo, MultipartFile file);

    /**
     * 修改容量表基本信息
     *
     * @param tableInfo
     * @return
     */
    int updateTableInfo(TableInfo tableInfo);

    /**
     * 修改容量表状态
     *
     * @param tableInfo
     * @return
     */
    int updateTableStatus(TableInfo tableInfo);

    /**
     * 获取容量表列表
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param tankFieldId
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, Integer tankFieldId);

    /**
     * 导出容量表
     *
     * @param tableInfo
     * @return
     */
    ResponseEntity<byte[]> export(TableInfo tableInfo);

    /**
     * 根据tankid获取正在用的容量表基本信息
     *
     * @param tankId
     * @return
     */
    TableInfo getTableInfoByTankId(Integer tankId);

    /**
     * 删除容量表
     *
     * @param tableId
     */
    void deleteTable(Integer tableId);
}
