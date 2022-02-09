package com.oket.ccic.service.impl;

import com.oket.ccic.entity.SampleReportBase;
import com.oket.ccic.entity.SampleReportContent;
import com.oket.ccic.mapper.SampleReportMapper;
import com.oket.ccic.service.SampleReportService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.SampleReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 取样报告单服务接口实现类
 * @author: dzp
 * @create: 2021-08-21 16:05
 **/
@Service
public class SampleReportServiceImpl implements SampleReportService {

    @Autowired
    private SampleReportMapper sampleReportMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSampleReport(SampleReportVo sampleReportVo) {
        //添加基本信息
        SampleReportBase sampleReportBase = new SampleReportBase();
        BeanUtils.copyProperties(sampleReportVo, sampleReportBase);
        sampleReportMapper.addSampleReportBase(sampleReportBase);
        //添加内容信息
        List<SampleReportContent> sampleReportContents = sampleReportVo.getSampleReportContents();
        if (sampleReportContents != null && sampleReportContents.size() > 0) {
            for (SampleReportContent sampleReport : sampleReportContents) {
                sampleReport.setSampleReportBaseId(sampleReportBase.getId());
            }
        }
        sampleReportMapper.addSampleReportContents(sampleReportContents);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, Date startTime, Date endTime, String shipName, String principal) {
        Integer companyId = shiroUtils.getCompanyId();
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        List<SampleReportVo> sampleReportVos = sampleReportMapper.getPage(pageNum, pageSize, verificationNo, startTime, endTime, companyId, shipName, principal);
        Long pageTotal = sampleReportMapper.getPageTotal(verificationNo, startTime, endTime, companyId, shipName, principal);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(sampleReportVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }
}
