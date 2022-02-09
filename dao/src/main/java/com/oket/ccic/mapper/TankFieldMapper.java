package com.oket.ccic.mapper;

import com.oket.ccic.entity.TankField;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: Layer
 * @description: 罐区mapper
 * @author: dzp
 * @create: 2021-08-04 15:57
 **/
@Repository
public interface TankFieldMapper {
    /**
     * 添加罐区
     *
     * @param tankField
     * @return
     */
    int addTankField(@Param("tankField") TankField tankField);

    /**
     * 查询罐区信息
     *
     * @param pageNum
     * @param pageSize
     * @param tankFieldName
     * @param companyId
     * @return
     */
    List<TankField> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("tankFieldName") String tankFieldName, @Param("companyId") Integer companyId);

    /**
     * 查询总数
     *
     * @param tankFieldName
     * @return
     */
    Long getPageTotal(@Param("tankFieldName") String tankFieldName);

    /**
     * 编辑罐区
     *
     * @param tankField
     * @return
     */
    int updateTankField(@Param("tankField") TankField tankField);

    /**
     * 根据罐区id修改罐区状态
     *
     * @param tankFieldId
     * @param status
     * @return
     */
    int updateTankFieldByTankFieldId(@Param("tankFieldId") Integer tankFieldId, @Param("status") Byte status);
}
