package com.oket.ccic.service;


import com.oket.ccic.entity.Company;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.CompanyPageArgs;

/**
 * @program: oketmicroservice
 * @description: 公司服务类
 * @author: dzp
 * @create: 2021-05-26 13:48
 **/
public interface CompanyService {
    /**
     * 添加公司
     *
     * @param company
     * @return
     */
    int addCompany(Company company);

    /**
     * 获取公司列表
     *
     * @param companyPageArgs
     * @return
     */
    RespPageBean getPage(CompanyPageArgs companyPageArgs);

    /**
     * 更新公司信息
     *
     * @param company
     * @return
     */
    int updateCompany(Company company);

    /**
     * 修改公司状态
     *
     * @param companyId
     * @param status
     * @return
     */
    int updateCompanyStatus(Integer companyId, Byte status);

    /**
     * 根据companyId获取公司状态
     *
     * @param companyId
     * @return
     */
    Byte getCompanyStatusByCompanyId(Integer companyId);
}
