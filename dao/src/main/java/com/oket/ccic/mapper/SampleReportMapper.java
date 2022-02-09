package com.oket.ccic.mapper;

import com.oket.ccic.entity.SampleReportBase;
import com.oket.ccic.entity.SampleReportContent;
import com.oket.ccic.vo.SampleReportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 取样报告单Mapper
 * @author: dzp
 * @create: 2021-08-21 16:08
 **/
@Repository
public interface SampleReportMapper {

    /**
     * 添加取样报告单基本信息
     *
     * @param sampleReportBase
     * @return
     */
    int addSampleReportBase(@Param("sampleReportBase") SampleReportBase sampleReportBase);

    /**
     * 批量保存取样报告内容
     *
     * @param sampleReportContents
     * @return
     */
    int addSampleReportContents(@Param("sampleReportContents") List<SampleReportContent> sampleReportContents);

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
    List<SampleReportVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
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
                      @Param("principal") String principal);
}
