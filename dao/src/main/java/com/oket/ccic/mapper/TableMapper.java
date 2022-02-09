package com.oket.ccic.mapper;


import com.oket.ccic.entity.TableDetail;
import com.oket.ccic.entity.TableInfo;
import com.oket.ccic.vo.TableInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: oketmicroservice
 * @description: 容量表Mapper
 * @author: dzp
 * @create: 2021-05-27 10:38
 **/
@Repository
public interface TableMapper {
    /**
     * 根据tankId获取对应的容量基本信息表
     *
     * @param tankId
     * @return
     */
    TableInfo getTableInfoByTankId(@Param("tankId") Integer tankId);

    /**
     * 根据容量表基本信息表id查询明细表
     *
     * @param tableInfoId
     * @return
     */
    List<TableDetail> getTableDetailByTableInfoId(@Param("tableInfoId") Integer tableInfoId);

    /**
     * 批量添加容量表明细
     *
     * @param list
     * @return
     */
    Integer addTableDetails(@Param("list") List<TableDetail> list);

    /**
     * 添加容量表基础信息
     *
     * @param tableInfo
     */
    void addTableInfo(@Param("tableInfo") TableInfo tableInfo);

    /**
     * 修改容量基本信息表总高度和底量高度
     *
     * @param tableInfoId
     * @param baseAmountHeight
     * @param totalHeight
     * @return
     */
    int updateTableInfoHeight(@Param("tableInfoId") Integer tableInfoId, @Param("baseAmountHeight") BigDecimal baseAmountHeight, @Param("totalHeight") BigDecimal totalHeight);

    /**
     * 查询在用的容量表
     *
     * @param tankId
     * @return
     */
    int queryUseTableByTankId(@Param("tankId") Integer tankId);

    /**
     * 修改容量表基本信息
     *
     * @param tableInfo
     * @return
     */
    int updateTableInfo(@Param("tableInfo") TableInfo tableInfo);

    /**
     * 修改容量表状态
     *
     * @param tableInfoId
     * @param status
     * @return
     */
    int updateTableStatus(@Param("tableInfoId") Integer tableInfoId, @Param("status") Byte status);

    /**
     * 分页查询容量表
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param tankFieldId
     * @return
     */
    List<TableInfoVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("companyId") Integer companyId, @Param("tankFieldId") Integer tankFieldId);

    /**
     * 分页查询总数
     *
     * @param companyId
     * @param tankFieldId
     * @return
     */
    long getPageTotal(@Param("companyId") Integer companyId, @Param("tankFieldId") Integer tankFieldId);

    /**
     * 删除容量表基本信息
     *
     * @param tableId
     */
    void deleteTableInfo(@Param("tableId") Integer tableId);

    /**
     * 删除容量表内容信息
     *
     * @param tableId
     */
    void deleteTableDetail(@Param("tableId") Integer tableId);
}
