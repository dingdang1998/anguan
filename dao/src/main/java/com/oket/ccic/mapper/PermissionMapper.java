package com.oket.ccic.mapper;

import com.oket.ccic.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: Layer
 * @description: 权限mapper
 * @author: dzp
 * @create: 2021-08-11 14:21
 **/
@Repository
public interface PermissionMapper {

    /**
     * 根据用户id获取权限
     *
     * @param userId
     * @return
     */
    List<Permission> getPermissionsByUserId(@Param("userId") Integer userId);
}
