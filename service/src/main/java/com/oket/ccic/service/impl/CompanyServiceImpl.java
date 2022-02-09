package com.oket.ccic.service.impl;


import com.oket.ccic.entity.Company;
import com.oket.ccic.mapper.CompanyMapper;
import com.oket.ccic.service.CompanyService;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.CompanyPageArgs;
import com.oket.ccic.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: oketmicroservice
 * @description: 公司服务实现类
 * @author: dzp
 * @create: 2021-05-26 13:48
 **/
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public int addCompany(Company company) {
        return companyMapper.addCompany(company);
    }

    @Override
    public RespPageBean getPage(CompanyPageArgs companyPageArgs) {
        Integer pageNum = companyPageArgs.getPageNum();
        Integer pageSize = companyPageArgs.getPageSize();
        String companyName = companyPageArgs.getCompanyName();
        Integer companyId = companyPageArgs.getCompanyId();
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        List<CompanyVo> companyVos = companyMapper.getPage(pageNum, pageSize, companyName, companyId);
        Long pageTotal = companyMapper.getPageTotal(companyName);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(companyVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    public int updateCompany(Company company) {
        return companyMapper.updateCompany(company);
    }

    @Override
    public int updateCompanyStatus(Integer companyId, Byte status) {
        return companyMapper.updateStatusByCompanyId(companyId, status);
    }

    @Override
    public Byte getCompanyStatusByCompanyId(Integer companyId) {
        return companyMapper.getCompanyStatusByCompanyId(companyId);
    }
}
