package com.oket.ccic.mapper;

import com.oket.ccic.entity.ComputingLogBase;
import com.oket.ccic.entity.ComputingLogContent;
import com.oket.ccic.vo.ComputingLogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-23 17:28
 **/
@Repository
public interface ComputingLogMapper {

    /**
     * 添加计算记录基本信息
     *
     * @param computingLogBase
     * @return
     */
    int addComputingLogBase(@Param("computingLogBase") ComputingLogBase computingLogBase);

    /**
     * 批量保存计算记录内容信息
     *
     * @param computingLogContents
     * @return
     */
    int saveComputingLogContents(@Param("computingLogContents") List<ComputingLogContent> computingLogContents);

    /**
     * 分页查询计算记录基本信息
     *
     * @param pageNum
     * @param pageSize
     * @param verificationNo
     * @param startTime
     * @param endTime
     * @param status
     * @param companyId
     * @param type
     * @param type2
     * @param shipName
     * @param principal
     * @return
     */
    List<ComputingLogVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
                                 @Param("verificationNo") String verificationNo,
                                 @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                 @Param("status") Byte status, @Param("companyId") Integer companyId,
                                 @Param("type") Byte type, @Param("type2") Byte type2,
                                 @Param("shipName") String shipName, @Param("principal") String principal);

    /**
     * 分页查询总数
     *
     * @param verificationNo
     * @param startTime
     * @param endTime
     * @param status
     * @param shipName
     * @param principal
     * @return
     */
    Long getPageTotal(@Param("verificationNo") String verificationNo,
                      @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                      @Param("status") Byte status, @Param("shipName") String shipName, @Param("principal") String principal);

    /**
     * 根据id查找计算记录
     *
     * @param computingLogBaseId
     * @return
     */
    ComputingLogVo getComputingLogById(@Param("computingLogBaseId") Long computingLogBaseId);

    /**
     * 根据id获取计算记录内容信息
     *
     * @param computingLogContentId
     * @return
     */
    ComputingLogContent getComputingLogContentById(@Param("computingLogContentId") Long computingLogContentId);

    /**
     * 修改计算记录状态
     *
     * @param computingLogBaseId
     * @param status
     * @return
     */
    int updateStatus(@Param("computingLogBaseId") Long computingLogBaseId, @Param("status") Byte status);
}
