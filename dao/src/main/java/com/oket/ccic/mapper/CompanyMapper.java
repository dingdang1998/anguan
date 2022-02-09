package com.oket.ccic.mapper;

import com.oket.ccic.entity.Company;
import com.oket.ccic.vo.CompanyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: oketmicroservice
 * @description: 公司Mapper
 * @author: dzp
 * @create: 2021-05-26 13:56
 **/
@Repository
public interface CompanyMapper {

    /**
     * 添加公司
     *
     * @param company
     * @return
     */
    int addCompany(@Param("company") Company company);

    /**
     * 分页查询公司
     *
     * @param pageNum
     * @param pageSize
     * @param companyName
     * @return
     */
    List<CompanyVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("companyName") String companyName, @Param("companyId") Integer companyId);

    /**
     * 分页查询总数
     *
     * @param companyName
     * @return
     */
    Long getPageTotal(@Param("companyName") String companyName);

    /**
     * 获取公司下可创建的账号个数
     *
     * @param companyId
     * @return
     */
    Integer getAccountNum(@Param("companyId") Integer companyId);

    /**
     * 修改公司信息
     *
     * @param company
     * @return
     */
    int updateCompany(@Param("company") Company company);

    /**
     * 根据公司id修改公司状态
     *
     * @param companyId
     * @param status
     * @return
     */
    int updateStatusByCompanyId(@Param("companyId") Integer companyId, @Param("status") Byte status);

    /**
     * 根据companyId获取公司状态
     *
     * @param companyId
     * @return
     */
    Byte getCompanyStatusByCompanyId(@Param("companyId") Integer companyId);
}
