package com.oket.ccic.service.impl;

import com.oket.ccic.entity.TestingTool;
import com.oket.ccic.mapper.TestingToolMapper;
import com.oket.ccic.service.TestingToolService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.TestingToolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Layer
 * @description: 检验工具服务接口实现类
 * @author: dzp
 * @create: 2021-08-09 14:40
 **/
@Service
public class TestingToolServiceImpl implements TestingToolService {

    @Autowired
    private TestingToolMapper testingToolMapper;
    @Autowired
    private ShiroUtils shiroUtils;

    @Override
    public int addTestingTool(TestingTool testingTool) {
        return testingToolMapper.addTestingTool(testingTool);
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        if (companyId == null) {
            companyId = shiroUtils.getCompanyId();
        }
        List<TestingToolVo> toolVos = testingToolMapper.getPage(pageNum, pageSize, companyId);
        Long pageTotal = testingToolMapper.getPageTotal(companyId);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(toolVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public int updateTestingTool(TestingTool testingTool) {
        return testingToolMapper.updateTestingTool(testingTool);
    }

    @Override
    public int updateTestingToolStatus(Integer toolId, Byte status) {
        return testingToolMapper.updateTestingToolStatus(toolId, status);
    }
}
