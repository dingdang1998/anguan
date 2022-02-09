package com.oket.ccic.service.impl;

import com.oket.ccic.entity.Notice;
import com.oket.ccic.mapper.NoticeMapper;
import com.oket.ccic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 公告服务实现类
 * @author: dzp
 * @create: 2021-08-18 10:14
 **/
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public int addNotice(Notice notice) {
        notice.setCreateTime(new Date());
        return noticeMapper.addNotice(notice);
    }

    @Override
    public List<Notice> get(Date startTime, Date endTime, Byte status) {
        return noticeMapper.get(startTime, endTime, status);
    }

    @Override
    public int update(Notice notice) {
        return noticeMapper.update(notice);
    }

    @Override
    public int updateNoticeStatus(Integer noticeId, Byte status) {
        return noticeMapper.updateNoticeStatus(noticeId, status);
    }

    @Override
    public int deleteNotice(Integer noticeId) {
        return noticeMapper.deleteNotice(noticeId);
    }
}
