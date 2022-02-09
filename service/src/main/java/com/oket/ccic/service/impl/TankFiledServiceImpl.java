package com.oket.ccic.service.impl;

import com.oket.ccic.entity.TankField;
import com.oket.ccic.mapper.TankFieldMapper;
import com.oket.ccic.service.TankFiledService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Layer
 * @description: 罐区服务接口实现类
 * @author: dzp
 * @create: 2021-08-04 15:55
 **/
@Service
public class TankFiledServiceImpl implements TankFiledService {

    @Autowired
    private TankFieldMapper tankFieldMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public int addTankField(TankField tankField) {
        return tankFieldMapper.addTankField(tankField);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, String tankFieldName, Integer companyId) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        companyId = shiroUtils.getCompanyId();
        List<TankField> companyVos = tankFieldMapper.getPage(pageNum, pageSize, tankFieldName, companyId);
        Long pageTotal = tankFieldMapper.getPageTotal(tankFieldName);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(companyVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public int updateTankField(TankField tankField) {
        return tankFieldMapper.updateTankField(tankField);
    }

    @Override
    public int updateTankFieldStatus(Integer tankFieldId, Byte status) {
        return tankFieldMapper.updateTankFieldByTankFieldId(tankFieldId, status);
    }
}
