package com.oket.ccic.service;

import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.ComputingLogVo;
import com.oket.ccic.vo.ProfitAndLossVo;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-23 17:26
 **/
public interface ComputingLogService {

    /**
     * 添加计算记录
     *
     * @param computingLogVo
     */
    void addComputingLog(ComputingLogVo computingLogVo);

    /**
     * 分页查询计算记录
     *
     * @param pageNum
     * @param pageSize
     * @param verificationNo
     * @param shipName
     * @param startTime
     * @param endTime
     * @param principal
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, String shipName, Date startTime, Date endTime,String principal);

    /**
     * 获取损益
     *
     * @param companyId
     * @param type
     * @param type2
     * @param shipName
     * @param startTime
     * @param endTime
     * @param principal
     * @return
     */
    List<ProfitAndLossVo> getProfitAndLossByCompanyId(Integer companyId, Byte type, Byte type2, String shipName, Date startTime, Date endTime,String principal);

    /**
     * 根据id获取计算记录
     *
     * @param computingLogBaseId
     * @return
     */
    ComputingLogVo getComputingLogById(Long computingLogBaseId);

    /**
     * 修改计算记录状态
     *
     * @param computingLogBaseId
     * @param status
     * @return
     */
    int update(Long computingLogBaseId, Byte status);

    /**
     * 出单记录日志
     *
     * @param type
     * @param id
     */
    void log(Byte type, String id);
}
