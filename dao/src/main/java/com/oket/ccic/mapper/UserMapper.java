package com.oket.ccic.mapper;


import com.oket.ccic.entity.Role;
import com.oket.ccic.entity.User;
import com.oket.ccic.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author dzp
 */
@Repository
public interface UserMapper {

    /**
     * 添加用户
     *
     * @param user
     */
    void addUser(@Param("user") User user);

    /**
     * 分页查询用户
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param userName
     * @param userId    不查询当前登录用户
     * @return
     */
    List<UserVo> getPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("companyId") Integer companyId, @Param("userName") String userName, @Param("userId") Integer userId);

    /**
     * 分页查询总数
     *
     * @param companyId
     * @param userName
     * @return
     */
    Long getPageTotal(@Param("companyId") Integer companyId, @Param("userName") String userName);

    /**
     * 添加用户-公司关联关系
     *
     * @param userId
     * @param companyId
     */
    void addUserCompanyRelation(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 添加用户-角色关联关系
     *
     * @param userId
     * @param roleId
     */
    void addUserRoleRelation(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void updateUser(@Param("user") User user);

    /**
     * 修改用户-公司关联关系
     *
     * @param userId
     * @param companyId
     */
    void updateUserCompanyRelation(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 修改用户-角色关联关系
     *
     * @param userId
     * @param roleId
     */
    void updateUserRoleRelation(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @return
     */
    int updateUserStatus(@Param("userId") Integer userId, @Param("status") Byte status);

    /**
     * 修改登录时间
     *
     * @param now
     * @param userId
     * @return
     */
    Integer updateLastLoginTime(@Param("now") Date now, @Param("userId") Integer userId);

    /**
     * 根据用户名修改密码
     *
     * @param password
     * @param username
     * @return
     */
    int updatePassword(@Param("password") String password, @Param("username") String username);

    /**
     * 检查用户名是否存在
     *
     * @param userName
     * @return
     */
    int checkUserName(@Param("userName") String userName);

    /**
     * 检查邮箱是否存在
     *
     * @param mailAddress
     * @return
     */
    int checkMailAddress(@Param("mailAddress") String mailAddress);

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    UserVo getUserVoByUsername(@Param("userName") String userName);

    /**
     * 根据Id获取角色
     *
     * @param roleIds
     * @return
     */
    List<Role> getRolesById(@Param("roleIds") List<Integer> roleIds);

    /**
     * 获取子角色id列表
     *
     * @param roleId
     * @return
     */
    String getRoleChildrenIds(@Param("roleId") Integer roleId);

    /**
     * 查询公司下的账号个数
     *
     * @param companyId
     * @return
     */
    Integer getUserNumByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 根据用户名查询邮箱
     *
     * @param username
     * @return
     */
    String getMailAddressByUserName(@Param("username") String username);

    /**
     * 根据id查找密码
     *
     * @param userId
     * @return
     */
    String getPasswordByUserId(@Param("userId") Integer userId);

    /**
     * 根据id查找用户
     *
     * @param userId
     * @return
     */
    User getUserByUserId(@Param("userId") Integer userId);

    /**
     * 查找该用户所属组织下的管理员
     *
     * @param companyId
     * @return
     */
    User getAdminByCompanyId(@Param("companyId") Integer companyId);
}
