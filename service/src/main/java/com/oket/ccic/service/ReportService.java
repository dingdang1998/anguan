package com.oket.ccic.service;

import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.ReportVo;

import java.util.Date;

/**
 * @program: Layer
 * @description: 工作记录单服务接口
 * @author: dzp
 * @create: 2021-08-21 15:54
 **/
public interface ReportService {

    /**
     * 添加工作记录单
     *
     * @param reportVo
     */
    void addReport(ReportVo reportVo);

    /**
     * 分页查询工作记录单
     *
     * @param pageNum
     * @param pageSize
     * @param verificationNo
     * @param startTime
     * @param endTime
     * @param shipName
     * @param principal
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, Date startTime, Date endTime, String shipName, String principal);
}
