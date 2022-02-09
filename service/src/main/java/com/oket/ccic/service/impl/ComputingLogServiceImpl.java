package com.oket.ccic.service.impl;

import com.oket.ccic.entity.ComputingLogBase;
import com.oket.ccic.entity.ComputingLogContent;
import com.oket.ccic.mapper.ComputingLogMapper;
import com.oket.ccic.service.ComputingLogService;
import com.oket.ccic.util.CustomTimeUtils;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.Constants;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.ComputingLogVo;
import com.oket.ccic.vo.ProfitAndLossVo;
import com.oket.ccic.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-23 17:26
 **/
@Service
public class ComputingLogServiceImpl implements ComputingLogService {

    private static final Logger logger = LoggerFactory.getLogger(ComputingLogServiceImpl.class);

    @Autowired
    private ComputingLogMapper computingLogMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComputingLog(ComputingLogVo computingLogVo) {
        //保存计算记录表基本信息
        ComputingLogBase computingLogBase = new ComputingLogBase();
        BeanUtils.copyProperties(computingLogVo, computingLogBase);
        //补充信息
        computingLogBase.setCreateTime(new Date());
        computingLogMapper.addComputingLogBase(computingLogBase);
        //保存计算记录内容信息
        Long computingLogBaseId = computingLogBase.getId();
        List<ComputingLogContent> computingLogContents = computingLogVo.getComputingLogContents();
        if (computingLogContents != null && computingLogContents.size() > 0) {
            for (ComputingLogContent computingLogContent : computingLogContents) {
                computingLogContent.setComputingLogBaseId(computingLogBaseId);
            }
        }
        computingLogMapper.saveComputingLogContents(computingLogContents);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, String verificationNo, String shipName, Date startTime, Date endTime, String principal) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        //admin和普通管理员展示全部，其余用户只展示状态为正常的计算记录
        Byte status = null;
        UserVo loginUserVo = shiroUtils.getLoginUserVo();
        Integer roleId = loginUserVo.getRoleId();
        if (roleId != Constants.ADMIN_ID && roleId != Constants.N_ADMIN_ID) {
            status = 1;
        }
        List<ComputingLogVo> computingLogVos = computingLogMapper.getPage(pageNum, pageSize, verificationNo, startTime, endTime, status, loginUserVo.getCompanyId(), null, null, shipName, principal);
        Long pageTotal = computingLogMapper.getPageTotal(verificationNo, startTime, endTime, status, shipName, principal);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(computingLogVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public List<ProfitAndLossVo> getProfitAndLossByCompanyId(Integer companyId, Byte type, Byte type2, String shipName, Date startTime, Date endTime, String principal) {
        List<ComputingLogVo> computingLogVos = computingLogMapper.getPage(null, null, null, startTime, endTime, new Byte("1"), companyId, type, type2, shipName, principal);
        return computingLogVos.stream().map(computingLogVo -> new ProfitAndLossVo(
                computingLogVo.getVerificationNo(), computingLogVo.getPalStere(),
                computingLogVo.getPalMt(), computingLogVo.getPalSterePercentage(),
                computingLogVo.getPalMtPercentage(), computingLogVo.getCreateTime(),
                computingLogVo.getShipName(), computingLogVo.getPrincipal())).collect(Collectors.toList());
    }

    @Override
    public ComputingLogVo getComputingLogById(Long computingLogBaseId) {
        return computingLogMapper.getComputingLogById(computingLogBaseId);
    }

    @Override
    public int update(Long computingLogBaseId, Byte status) {
        return computingLogMapper.updateStatus(computingLogBaseId, status);
    }

    @Override
    public void log(Byte type, String id) {
        String username = shiroUtils.getLoginUserVo().getUsername();

        switch (type) {
            case 1:
                logger.info(username + "--打印岸罐计算单--" + id + "--" + CustomTimeUtils.getCurrentTimeByFormatSecond());
                break;
            case 2:
                logger.info(username + "--打印测量记录单--" + id + "--" + CustomTimeUtils.getCurrentTimeByFormatSecond());
                break;
            case 3:
                logger.info(username + "--打印工作记录单--" + id + "--" + CustomTimeUtils.getCurrentTimeByFormatSecond());
                break;
            case 4:
                logger.info(username + "--打印取样报告--" + id + "--" + CustomTimeUtils.getCurrentTimeByFormatSecond());
                break;
            case 5:
                logger.info(username + "--打印证书--" + id + "--" + CustomTimeUtils.getCurrentTimeByFormatSecond());
                break;
            default:
                break;
        }
    }
}
