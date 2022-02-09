package com.oket.ccic.service;

import com.oket.ccic.entity.TestingTool;
import com.oket.ccic.util.base.RespPageBean;

/**
 * @program: Layer
 * @description: 计量器具服务接口
 * @author: dzp
 * @create: 2021-08-09 14:40
 **/
public interface TestingToolService {

    /**
     * 添加检验工具
     *
     * @param testingTool
     * @return
     */
    int addTestingTool(TestingTool testingTool);

    /**
     * 分页查询计量器具
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId);

    /**
     * 编辑计量工具信息
     *
     * @param testingTool
     * @return
     */
    int updateTestingTool(TestingTool testingTool);

    /**
     * 修改计量工具状态
     *
     * @param toolId
     * @param status
     * @return
     */
    int updateTestingToolStatus(Integer toolId, Byte status);
}
