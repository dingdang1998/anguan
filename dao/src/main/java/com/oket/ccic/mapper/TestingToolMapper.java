package com.oket.ccic.mapper;

import com.oket.ccic.entity.TestingTool;
import com.oket.ccic.vo.TestingToolVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: Layer
 * @description: 计量器具mapper
 * @author: dzp
 * @create: 2021-08-09 14:43
 **/
@Repository
public interface TestingToolMapper {

    /**
     * 添加计量器具
     *
     * @param testingTool
     * @return
     */
    int addTestingTool(@Param("testingTool") TestingTool testingTool);

    /**
     * 分页查询计量器具
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @return
     */
    List<TestingToolVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("companyId") Integer companyId);

    /**
     * 分页查询总数
     *
     * @param companyId
     * @return
     */
    Long getPageTotal(@Param("companyId") Integer companyId);

    /**
     * 编辑计量工具信息
     *
     * @param testingTool
     * @return
     */
    int updateTestingTool(@Param("testingTool") TestingTool testingTool);

    /**
     * 修改计量器具状态
     *
     * @param toolId
     * @param status
     * @return
     */
    int updateTestingToolStatus(@Param("toolId") Integer toolId, @Param("status") Byte status);
}
