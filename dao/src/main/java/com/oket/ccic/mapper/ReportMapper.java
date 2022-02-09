package com.oket.ccic.mapper;

import com.oket.ccic.entity.ReportBase;
import com.oket.ccic.entity.ReportContent;
import com.oket.ccic.vo.ReportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 工作记录单mapper
 * @author: dzp
 * @create: 2021-08-21 16:00
 **/
@Repository
public interface ReportMapper {

    /**
     * 添加工作记录基本信息
     *
     * @param reportBase
     * @return
     */
    int addReportBase(@Param("reportBase") ReportBase reportBase);

    /**
     * 批量添加工作记录内容信息
     *
     * @param reportContents
     * @return
     */
    int addReportContents(@Param("reportContents") List<ReportContent> reportContents);

    /**
     * 分页查询取样报告基本信息
     *
     * @param pageNum
     * @param pageSize
     * @param verificationNo
     * @param startTime
     * @param endTime
     * @param companyId
     * @param shipName
     * @param principal
     * @return
     */
    List<ReportVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
                           @Param("verificationNo") String verificationNo,
                           @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                           @Param("companyId") Integer companyId, @Param("shipName") String shipName,
                           @Param("principal") String principal);

    /**
     * 分页查询总数
     *
     * @param verificationNo
     * @param startTime
     * @param endTime
     * @param companyId
     * @param shipName
     * @param principal
     * @return
     */
    Long getPageTotal(@Param("verificationNo") String verificationNo,
                      @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                      @Param("companyId") Integer companyId, @Param("shipName") String shipName,
                      @Param("principal")String principal);
}
