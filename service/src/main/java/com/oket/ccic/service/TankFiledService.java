package com.oket.ccic.service;

import com.oket.ccic.entity.TankField;
import com.oket.ccic.util.base.RespPageBean;

/**
 * @program: Layer
 * @description: 罐区服务接口
 * @author: dzp
 * @create: 2021-08-04 15:54
 **/
public interface TankFiledService {
    /**
     * 添加罐区
     *
     * @param tankField
     * @return
     */
    int addTankField(TankField tankField);

    /**
     * 查询罐区信息
     *
     * @param pageNum
     * @param pageSize
     * @param tankFieldName
     * @param companyId
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, String tankFieldName, Integer companyId);

    /**
     * 修改罐区信息
     *
     * @param tankField
     * @return
     */
    int updateTankField(TankField tankField);

    /**
     * 修改罐区状态
     *
     * @param tankFieldId
     * @param status
     * @return
     */
    int updateTankFieldStatus(Integer tankFieldId, Byte status);
}
