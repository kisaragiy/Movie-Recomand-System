package com.it.movie.user.service.impl;

import com.it.movie.user.dao.UserMapper;
import com.it.movie.user.entity.User;
import com.it.movie.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * 用户服务实现类
 * 实现用户管理的具体业务逻辑
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        
        // 密码加密处理（如果需要）
        // String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        
        User user = userMapper.login(username, password);
        if (user != null && "active".equals(user.getStatus())) {
            // 清除密码信息，避免返回给前端
            user.setUpass(null);
            return user;
        }
        return null;
    }
    
    @Override
    public User register(User user) {
        if (user == null || user.getUname() == null || user.getUpass() == null) {
            throw new RuntimeException("用户信息不能为空");
        }
        
        // 检查用户名是否已存在
        if (checkUsernameExists(user.getUname())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (user.getEmail() != null && checkEmailExists(user.getEmail())) {
            throw new RuntimeException("邮箱已被使用");
        }
        
        // 设置默认值
        user.setAddtime(new Date());
        user.setStatus("active");
        user.setMembershipType("普通");
        user.setMembershipActive(false);
        user.setLevel(1);
        user.setIsgly("否");
        
        // 密码加密（如果需要）
        // user.setUpass(DigestUtils.md5DigestAsHex(user.getUpass().getBytes()));
        
        int result = userMapper.add(user);
        if (result > 0) {
            user.setUpass(null); // 清除密码
            return user;
        }
        
        throw new RuntimeException("注册失败");
    }
    
    @Override
    public User getUserById(Integer id) {
        if (id == null) {
            return null;
        }
        
        User user = userMapper.findById(id);
        if (user != null) {
            user.setUpass(null); // 清除密码
        }
        return user;
    }
    
    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        
        User user = userMapper.findByUsername(username);
        if (user != null) {
            user.setUpass(null); // 清除密码
        }
        return user;
    }
    
    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        
        // 如果有新密码，进行加密
        if (user.getUpass() != null && !user.getUpass().isEmpty()) {
            // user.setUpass(DigestUtils.md5DigestAsHex(user.getUpass().getBytes()));
        }
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean deleteUser(Integer id) {
        if (id == null) {
            return false;
        }
        
        return userMapper.delete(id) > 0;
    }
    
    @Override
    public PageInfo<User> getUserList(Map<String, Object> params, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll(params);
        
        // 清除所有用户的密码信息
        users.forEach(user -> user.setUpass(null));
        
        return new PageInfo<>(users);
    }
    
    @Override
    public boolean checkUsernameExists(String username) {
        if (username == null) {
            return false;
        }
        
        return userMapper.checkUsernameExists(username) > 0;
    }
    
    @Override
    public boolean checkEmailExists(String email) {
        if (email == null) {
            return false;
        }
        
        return userMapper.checkEmailExists(email) > 0;
    }
    
    @Override
    public String getPasswordByUsernameAndPhone(String username, String phone) {
        if (username == null || phone == null) {
            return null;
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("uname", username);
        params.put("tel", phone);
        
        List<User> users = userMapper.selectAll(params);
        if (!users.isEmpty()) {
            return users.get(0).getUpass();
        }
        
        return null;
    }
    
    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            return false;
        }
        
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        
        // 验证旧密码
        // String encryptedOldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!oldPassword.equals(user.getUpass())) {
            throw new RuntimeException("原密码不正确");
        }
        
        // 更新新密码
        // String encryptedNewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        user.setUpass(newPassword);
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean resetPassword(Integer userId, String newPassword) {
        if (userId == null || newPassword == null) {
            return false;
        }
        
        User user = new User();
        user.setId(userId);
        // user.setUpass(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        user.setUpass(newPassword);
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean updateUserStatus(Integer userId, String status) {
        if (userId == null || status == null) {
            return false;
        }
        
        return userMapper.updateStatus(userId, status) > 0;
    }
    
    @Override
    public boolean upgradeMembership(Integer userId, String membershipType, int months) {
        if (userId == null || membershipType == null) {
            return false;
        }
        
        // 计算到期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        Date expireDate = calendar.getTime();
        
        return userMapper.upgradeMembership(userId, membershipType, expireDate) > 0;
    }
    
    @Override
    public long getUserCount(Map<String, Object> params) {
        return userMapper.countUsers(params);
    }
    
    @Override
    public boolean batchDeleteUsers(List<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return false;
        }
        
        return userMapper.batchDelete(userIds) > 0;
    }
    
    @Override
    public User createUser(User user) { 
        return register(user); 
    }
}
