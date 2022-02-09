package com.oket.ccic.service;

import com.oket.ccic.entity.Notice;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 公告服务接口
 * @author: dzp
 * @create: 2021-08-18 10:14
 **/
public interface NoticeService {

    /**
     * 添加公告
     *
     * @param notice
     * @return
     */
    int addNotice(Notice notice);

    /**
     * 获取公告
     *
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    List<Notice> get(Date startTime, Date endTime, Byte status);

    /**
     * 编辑公告
     *
     * @param notice
     * @return
     */
    int update(Notice notice);

    /**
     * 修改公告状态
     *
     * @param noticeId
     * @param status
     * @return
     */
    int updateNoticeStatus(Integer noticeId, Byte status);

    /**
     * 删除公告
     *
     * @param noticeId
     * @return
     */
    int deleteNotice(Integer noticeId);
}
