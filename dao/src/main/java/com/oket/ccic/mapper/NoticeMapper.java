package com.oket.ccic.mapper;

import com.oket.ccic.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: Layer
 * @description: 公告Mapper
 * @author: dzp
 * @create: 2021-08-18 10:16
 **/
@Repository
public interface NoticeMapper {

    /**
     * 添加公告
     *
     * @param notice
     * @return
     */
    int addNotice(@Param("notice") Notice notice);

    /**
     * 获取公告
     *
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    List<Notice> get(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("status") Byte status);

    /**
     * 编辑公告
     *
     * @param notice
     * @return
     */
    int update(@Param("notice") Notice notice);

    /**
     * 修改公告状态
     *
     * @param noticeId
     * @param status
     * @return
     */
    int updateNoticeStatus(@Param("noticeId") Integer noticeId, @Param("status") Byte status);

    /**
     * 删除公告
     *
     * @param noticeId
     * @return
     */
    int deleteNotice(@Param("noticeId") Integer noticeId);
}
