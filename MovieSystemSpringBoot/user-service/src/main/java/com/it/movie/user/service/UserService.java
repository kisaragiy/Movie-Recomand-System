package com.it.movie.user.service;

import com.it.movie.user.entity.User;
import com.github.pagehelper.PageInfo;
import java.util.Map;
import java.util.List;

/**
 * 用户服务接口
 * 定义用户管理的核心业务逻辑
 */
public interface UserService {
    
    /**
     * 用户登录
     */
    User login(String username, String password);
    
    /**
     * 用户注册
     */
    User register(User user);
    
    /**
     * 根据ID获取用户信息
     */
    User getUserById(Integer id);
    
    /**
     * 根据用户名获取用户信息
     */
    User getUserByUsername(String username);
    
    /**
     * 更新用户信息
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     */
    boolean deleteUser(Integer id);
    
    /**
     * 分页查询用户列表
     */
    PageInfo<User> getUserList(Map<String, Object> params, int pageNum, int pageSize);
    
    /**
     * 检查用户名是否存在
     */
    boolean checkUsernameExists(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean checkEmailExists(String email);
    
    /**
     * 找回密码
     */
    String getPasswordByUsernameAndPhone(String username, String phone);
    
    /**
     * 修改密码
     */
    boolean changePassword(Integer userId, String oldPassword, String newPassword);
    
    /**
     * 重置密码
     */
    boolean resetPassword(Integer userId, String newPassword);
    
    /**
     * 用户状态管理
     */
    boolean updateUserStatus(Integer userId, String status);
    
    /**
     * 会员升级
     */
    boolean upgradeMembership(Integer userId, String membershipType, int months);
    
    /**
     * 统计用户数量
     */
    long getUserCount(Map<String, Object> params);
    
    /**
     * 批量操作
     */
    boolean batchDeleteUsers(List<Integer> userIds);
    
    /**
     * 创建用户（兼容方法）
     */
    User createUser(User user);
}