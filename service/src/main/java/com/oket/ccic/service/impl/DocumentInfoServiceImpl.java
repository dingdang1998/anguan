package com.oket.ccic.service.impl;

import com.oket.ccic.entity.DocumentInfo;
import com.oket.ccic.mapper.DocumentInfoMapper;
import com.oket.ccic.service.DocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-09-02 09:23
 **/
@Service
public class DocumentInfoServiceImpl implements DocumentInfoService {

    @Autowired
    private DocumentInfoMapper documentInfoMapper;

    @Override
    public int addDocumentInfo(DocumentInfo documentInfo) {
        return documentInfoMapper.addDocumentInfo(documentInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addDocumentInfos(List<DocumentInfo> documentInfos) {
        //1、把原来的基本信息删除
        documentInfoMapper.deleteDocumentInfosByCompanyId((long) documentInfos.get(0).getCompanyId());
        //2、添加新的基础信息
        return documentInfoMapper.addDocumentInfos(documentInfos);
    }

    @Override
    public Map<Byte, List<DocumentInfo>> get(Integer companyId) {
        List<DocumentInfo> documentInfos = documentInfoMapper.get(companyId);
        return documentInfos.stream().collect(Collectors.groupingBy(DocumentInfo::getType));
    }

    @Override
    public int update(DocumentInfo documentInfo) {
        return documentInfoMapper.update(documentInfo);
    }

    @Override
    public int delete(Long documentId) {
        return documentInfoMapper.delete(documentId);
    }
}
