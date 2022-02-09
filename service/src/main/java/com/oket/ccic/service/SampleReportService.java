package com.oket.ccic.service;

import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.SampleReportVo;

import java.util.Date;

/**
 * @program: Layer
 * @description: 取样报告单服务接口
 * @author: dzp
 * @create: 2021-08-21 16:04
 **/
public interface SampleReportService {

    /**
     * 添加取样报告单
     *
     * @param sampleReportVo
     */
    void addSampleReport(SampleReportVo sampleReportVo);

    /**
     * 分页查询取样报告
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
    RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, Date startTime, Date endTime, String shipName,String principal);
}
