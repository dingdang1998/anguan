package com.oket.ccic.mapper;

import com.oket.ccic.entity.DocumentInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-09-02 09:24
 **/
@Repository
public interface DocumentInfoMapper {

    /**
     * 添加单据维护信息
     *
     * @param documentInfo
     * @return
     */
    int addDocumentInfo(@Param("documentInfo") DocumentInfo documentInfo);

    /**
     * 批量添加单据基本信息
     *
     * @param documentInfos
     * @return
     */
    int addDocumentInfos(@Param("documentInfos") List<DocumentInfo> documentInfos);

    /**
     * 获取单据维护信息
     *
     * @param companyId
     * @return
     */
    List<DocumentInfo> get(@Param("companyId") Integer companyId);

    /**
     * 修改单据维护信息
     *
     * @param documentInfo
     * @return
     */
    int update(@Param("documentInfo") DocumentInfo documentInfo);

    /**
     * 删除单据维护信息
     *
     * @param documentId
     * @return
     */
    int delete(@Param("documentId") Long documentId);

    /**
     * 批量删除公司底下的表单基本信息
     *
     * @param companyId
     * @return
     */
    int deleteDocumentInfosByCompanyId(@Param("companyId") Long companyId);
}
