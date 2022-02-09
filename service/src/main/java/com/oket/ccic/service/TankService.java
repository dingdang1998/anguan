package com.oket.ccic.service;

import com.oket.ccic.entity.Tank;
import com.oket.ccic.util.base.RespPageBean;

/**
 * @program: Layer
 * @description: 岸罐服务接口
 * @author: dzp
 * @create: 2021-08-04 18:19
 **/
public interface TankService {

    /**
     * 添加岸罐
     *
     * @param tank
     * @return
     */
    int addTank(Tank tank);

    /**
     * 分页查询岸罐
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param tankFieldId
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, Integer tankFieldId);


    /**
     * 更新岸罐信息
     *
     * @param tank
     * @return
     */
    int updateTank(Tank tank);

    /**
     * 修改岸罐状态
     *
     * @param tankId
     * @param status
     * @return
     */
    int updateTankStatus(Integer tankId, Byte status);
}
