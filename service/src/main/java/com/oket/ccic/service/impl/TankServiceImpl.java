package com.oket.ccic.service.impl;

import com.oket.ccic.entity.Tank;
import com.oket.ccic.mapper.TankMapper;
import com.oket.ccic.service.TankService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.TankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Layer
 * @description: 岸罐服务接口实现类
 * @author: dzp
 * @create: 2021-08-04 18:22
 **/
@Service
public class TankServiceImpl implements TankService {

    @Autowired
    private TankMapper tankMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public int addTank(Tank tank) {
        return tankMapper.addTank(tank);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, Integer tankFieldId) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        if (companyId == null) {
            companyId = shiroUtils.getCompanyId();
        }
        List<TankVo> tankVos = tankMapper.getPage(pageNum, pageSize, companyId, tankFieldId);
        Long pageTotal = tankMapper.getPageTotal(companyId, tankFieldId);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(tankVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public int updateTank(Tank tank) {
        return tankMapper.updateTank(tank);
    }

    @Override
    public int updateTankStatus(Integer tankId, Byte status) {
        return tankMapper.updateStatusByTankId(tankId, status);
    }
}
