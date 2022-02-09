package com.oket.ccic.service.impl;


import com.oket.ccic.entity.Permission;
import com.oket.ccic.entity.Role;
import com.oket.ccic.entity.User;
import com.oket.ccic.mapper.CompanyMapper;
import com.oket.ccic.mapper.PermissionMapper;
import com.oket.ccic.mapper.UserMapper;
import com.oket.ccic.service.MailService;
import com.oket.ccic.service.UserService;
import com.oket.ccic.service.util.SendMailRunnable;
import com.oket.ccic.util.MailUtil;
import com.oket.ccic.util.PasswordUtils;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.Constants;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: storetank
 * @description: 用户服务接口实现类
 * @author: dzp
 * @create: 2021-05-25 18:48
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ShiroUtils shiroUtils;
    @Autowired
    private CompanyMapper companyMapper;
    @Value("${visitorCompanyId}")
    private Integer visitorCompanyId;
    @Value("${visitorRoleId}")
    private Integer visitorRoleId;
    @Autowired
    private MailService mailService;
    /**
     * 可以创建的账号个数
     */
    private static final String ACCOUNT_NUM = "accountNum";
    /**
     * 已经创建的账号个数
     */
    private static final String HAS_CREATED = "hasCreated";
    /**
     * 自定义无界线程池
     */
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            0,
            Integer.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            //不存储元素的阻塞队列
            new SynchronousQueue<>()
    );

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVo userVo) {
        //1、添加用户
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        //密码加密
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword()));
        //创建日期
        user.setCreatTime(new Date());
        //保存用户
        userMapper.addUser(user);
        //2、保存用户-公司关联关系 游客关联固定的测试公司
        userMapper.addUserCompanyRelation(user.getId(), visitorCompanyId);
        //3、保存用户角色关联关系 游客关联游客角色
        userMapper.addUserRoleRelation(user.getId(), visitorRoleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(UserVo userVo) {
        //1、添加用户
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        //记录下未加密的密码
        String passwordNoEn = user.getPassword();
        user.setCreatTime(new Date());
        //用户被谁创建
        user.setCreaterId(shiroUtils.getLoginUserVo().getId());
        //密码加密
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword()));
        //保存用户
        userMapper.addUser(user);
        //2、保存用户-公司关联关系
        userMapper.addUserCompanyRelation(user.getId(), userVo.getCompanyId());
        //3、保存用户-角色关联关系
        userMapper.addUserRoleRelation(user.getId(), userVo.getRoleId());
        //添加用户成功，发送邮件
        user.setPassword(passwordNoEn);
        threadPoolExecutor.execute(new SendMailRunnable(mailService, user, MailUtil.ADD_FLAG));
    }

    @Override
    public RespPageBean getPage(Integer pageNum, Integer pageSize, Integer companyId, String userName) {
        if (pageNum != null && pageSize != null) {
            pageNum = (pageNum - 1) * pageSize;
        }
        if (companyId == null) {
            companyId = shiroUtils.getCompanyId();
        }
        List<UserVo> userVos = userMapper.getPage(pageNum, pageSize, companyId, userName, shiroUtils.getLoginUserVo().getId());
        Long pageTotal = userMapper.getPageTotal(companyId, userName);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(userVos);
        respPageBean.setTotal(pageTotal);
        return respPageBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserVo userVo) {
        //1、修改用户信息
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setUpdateTime(new Date());
        userMapper.updateUser(user);
        //2、修改用户公司关联关系
        userMapper.updateUserCompanyRelation(user.getId(), userVo.getCompanyId());
        //3、修改用户角色关联关系
        userMapper.updateUserRoleRelation(user.getId(), userVo.getRoleId());
        //发送邮件
        threadPoolExecutor.execute(new SendMailRunnable(mailService, user, MailUtil.UPDATE_FLAG));
    }

    @Override
    public int updateUserStatus(Integer userId, Byte status) {
        userMapper.updateUserStatus(userId, status);
        //启用用户，向该用户发送邮件
        if (status == 1) {
            User user = userMapper.getUserByUserId(userId);
            //发送邮件
            threadPoolExecutor.execute(new SendMailRunnable(mailService, user, MailUtil.UPDATE_USER_STATUS_FLAG));
        }
        return 1;
    }

    @Override
    public int checkUserName(String userName) {
        return userMapper.checkUserName(userName);
    }

    @Override
    public int checkMailAddress(String mailAddress) {
        return userMapper.checkMailAddress(mailAddress);
    }

    @Override
    public List<Role> getRoles() {
        String roleChildrenIds = userMapper.getRoleChildrenIds(shiroUtils.getLoginUserVo().getRoleId());
        if (roleChildrenIds != null && !"".equals(roleChildrenIds)) {
            List<String> listStr = Arrays.asList(roleChildrenIds.split(","));
            List<Integer> roleIds = listStr.stream().map(Integer::parseInt).collect(Collectors.toList());
            return userMapper.getRolesById(roleIds);
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> getCreateUserNums() {
        Map<String, Integer> result = new HashMap<>();
        UserVo loginUserVo = shiroUtils.getLoginUserVo();
        if (loginUserVo.getRoleId() == Constants.ADMIN_ID) {
            //admin创建账号无限制
            result.put(ACCOUNT_NUM, null);
            result.put(HAS_CREATED, null);
        } else {
            //该公司下能创建几个账号
            Integer accountNum = companyMapper.getAccountNum(loginUserVo.getCompanyId());
            //该公司下已经创建了几个账号
            Integer hasCreated = userMapper.getUserNumByCompanyId(loginUserVo.getCompanyId());
            result.put(ACCOUNT_NUM, accountNum);
            result.put(HAS_CREATED, hasCreated);
        }
        return result;
    }

    @Override
    public Integer updateLastLoginTime(Date now, Integer userId) {
        return userMapper.updateLastLoginTime(now, userId);
    }

    @Override
    public String getMailAddressByUserName(String username) {
        return userMapper.getMailAddressByUserName(username);
    }

    @Override
    public int updatePassword(UserVo userVo) {
        //密码加密
        String encryptPassword = PasswordUtils.encryptPassword(userVo.getUsername(), userVo.getPassword());
        return userMapper.updatePassword(encryptPassword, userVo.getUsername());
    }

    @Override
    public boolean checkPassword(UserVo userVo) {
        String passwordByUserId = userMapper.getPasswordByUserId(userVo.getId());
        //给传过来的密码加密
        String encryptPassword = PasswordUtils.encryptPassword(userVo.getUsername(), userVo.getPassword());
        return passwordByUserId.equals(encryptPassword);
    }

    @Override
    public User getAdminByCompanyId(Integer companyId) {
        return userMapper.getAdminByCompanyId(companyId);
    }

    @Override
    public boolean checkCanCreateUser(Integer companyId) {
        //该公司下能创建几个账号
        Integer accountNum = companyMapper.getAccountNum(companyId);
        //该公司下已经创建了几个账号
        Integer hasCreated = userMapper.getUserNumByCompanyId(companyId);

        return hasCreated < accountNum;
    }

    @Override
    public UserVo getUserVoByUsername(String username) {
        return userMapper.getUserVoByUsername(username);
    }

    @Override
    public List<Permission> getUserPermissionsByUserId(Integer userId) {
        return permissionMapper.getPermissionsByUserId(userId);
    }
}
