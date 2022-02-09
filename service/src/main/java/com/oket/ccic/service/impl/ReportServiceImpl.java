package com.oket.ccic.service.impl;

import com.oket.ccic.entity.ReportBase;
import com.oket.ccic.entity.ReportContent;
import com.oket.ccic.mapper.ReportMapper;
import com.oket.ccic.service.ReportService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.ReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-21 15:56
 **/
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addReport(ReportVo reportVo) {
        //添加基本信息
        ReportBase reportBase = new ReportBase();
        BeanUtils.copyProperties(reportVo, reportBase);
        reportBase.setCreateTime(new Date());
        reportMapper.addReportBase(reportBase);
        //添加内容信息
        List<ReportContent> reportContents = reportVo.getReportContents();
        if (reportContents != null && reportContents.size() > 0) {
            for (ReportContent reportContent : reportContents) {
                reportContent.setReportBaseId(reportBase.getId());
            }
        }
        reportMapper.addReportContents(reportContents);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, Date startTime, Date endTime, String shipName, String principal) {
        Integer companyId = shiroUtils.getCompanyId();
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        List<ReportVo> reportVos = reportMapper.getPage(pageNum, pageSize, verificationNo, startTime, endTime, companyId, shipName, principal);
        Long pageTotal = reportMapper.getPageTotal(verificationNo, startTime, endTime, companyId, shipName, principal);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(reportVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }
}
