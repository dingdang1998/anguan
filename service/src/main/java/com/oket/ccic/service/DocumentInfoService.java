package com.oket.ccic.service;

import com.oket.ccic.entity.DocumentInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-09-02 09:22
 **/
public interface DocumentInfoService {

    /**
     * 添加单据维护信息
     *
     * @param documentInfo
     * @return
     */
    int addDocumentInfo(DocumentInfo documentInfo);

    /**
     * 批量添加单据维护信息
     *
     * @param documentInfos
     * @return
     */
    int addDocumentInfos(List<DocumentInfo> documentInfos);

    /**
     * 查询单据维护信息
     *
     * @param companyId
     * @return
     */
    Map<Byte, List<DocumentInfo>> get(Integer companyId);

    /**
     * 修改单据维护信息
     *
     * @param documentInfo
     * @return
     */
    int update(DocumentInfo documentInfo);

    /**
     * 删除单据维护信息
     *
     * @param documentId
     * @return
     */
    int delete(Long documentId);
}
