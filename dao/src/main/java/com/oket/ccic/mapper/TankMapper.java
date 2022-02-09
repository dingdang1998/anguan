package com.oket.ccic.mapper;

import com.oket.ccic.entity.Tank;
import com.oket.ccic.vo.TankVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: Layer
 * @description: 岸罐mapper
 * @author: dzp
 * @create: 2021-08-04 18:24
 **/
@Repository
public interface TankMapper {

    /**
     * 添加岸罐
     *
     * @param tank
     * @return
     */
    int addTank(@Param("tank") Tank tank);

    /**
     * 分页查询岸罐
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param tankFieldId
     * @return
     */
    List<TankVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("companyId") Integer companyId, @Param("tankFieldId") Integer tankFieldId);

    /**
     * 分页查询总数
     *
     * @param companyId
     * @param tankFieldId
     * @return
     */
    Long getPageTotal(@Param("companyId") Integer companyId, @Param("tankFieldId") Integer tankFieldId);


    /**
     * 修改岸罐信息
     *
     * @param tank
     * @return
     */
    int updateTank(@Param("tank") Tank tank);

    /**
     * 根据岸罐id修改岸罐状态
     *
     * @param tankId
     * @param status
     * @return
     */
    int updateStatusByTankId(@Param("tankId") Integer tankId, @Param("status") Byte status);
}
