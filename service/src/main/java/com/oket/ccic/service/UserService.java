package com.oket.ccic.service;


import com.oket.ccic.entity.Permission;
import com.oket.ccic.entity.Role;
import com.oket.ccic.entity.User;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.UserVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: storetank
 * @description: 用户服务接口
 * @author: dzp
 * @create: 2021-05-25 18:46
 **/
public interface UserService {

    /**
     * 用户注册
     *
     * @param userVo
     */
    void register(UserVo userVo);

    /**
     * 添加用户
     *
     * @param userVo
     */
    void add(UserVo userVo);

    /**
     * 分页查询计量器具
     *
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @param userName
     * @return
     */
    RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, String userName);

    /**
     * 编辑用户信息
     *
     * @param userVo
     */
    void update(UserVo userVo);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @return
     */
    int updateUserStatus(Integer userId, Byte status);

    /**
     * 通过用户名获取UserVo
     *
     * @param username
     * @return
     */
    UserVo getUserVoByUsername(String username);

    /**
     * 通过userId查询当前用户所具有的权限
     *
     * @param userId
     * @return
     */
    List<Permission> getUserPermissionsByUserId(Integer userId);

    /**
     * 检查用户名是否存在
     *
     * @param userName
     * @return
     */
    int checkUserName(String userName);

    /**
     * 检查邮箱是否存在
     *
     * @param mailAddress
     * @return
     */
    int checkMailAddress(String mailAddress);

    /**
     * 获取该用户能创建的用户角色类型
     * 只能创建比自己角色低的用户角色
     *
     * @return
     */
    List<Role> getRoles();

    /**
     * 检查该公司下账号个数是否已达上限
     *
     * @return
     */
    Map<String, Integer> getCreateUserNums();

    /**
     * 修改最后登录时间
     *
     * @param now
     * @param userId
     * @return
     */
    Integer updateLastLoginTime(Date now, Integer userId);

    /**
     * 获取用户邮箱
     *
     * @param username
     * @return
     */
    String getMailAddressByUserName(String username);

    /**
     * 修改密码
     *
     * @param userVo
     * @return
     */
    int updatePassword(UserVo userVo);

    /**
     * 校验密码
     *
     * @param userVo
     * @return
     */
    boolean checkPassword(UserVo userVo);

    /**
     * 查找该用户所属组织下的管理员
     *
     * @param companyId
     * @return
     */
    User getAdminByCompanyId(Integer companyId);

    /**
     * 检查该公司底下能否再创建账号
     *
     * @param companyId
     * @return
     */
    boolean checkCanCreateUser(Integer companyId);
}
