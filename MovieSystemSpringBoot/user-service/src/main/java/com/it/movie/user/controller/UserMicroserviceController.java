package com.it.movie.user.controller;

import com.it.movie.user.entity.User;
import com.it.movie.user.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户微服务控制器
 * 路径前缀: /microservice/api/user
 * 与原系统并行运行，不冲突
 */
@RestController
@RequestMapping("/microservice/api/user")
@CrossOrigin(origins = "*")
public class UserMicroserviceController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 微服务健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("service", "user-service");
        result.put("status", "UP");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    /**
     * 用户登录（微服务版本）
     * 与原系统路径 /Login 并行
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            User user = userService.login(loginUser.getUname(), loginUser.getUpass());
            if (user != null) {
                // 兼容原系统的Session格式
                request.getSession().setAttribute("sessionmember", user);
                result.put("data", 200);
                result.put("sessionmember", user);
                result.put("message", "微服务登录成功");
                result.put("source", "microservice");
            } else {
                result.put("data", 400);
                result.put("message", "用户名或密码错误");
            }
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "微服务登录失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 用户注册（微服务版本）
     * 与原系统路径 /Register 并行
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            User registeredUser = userService.register(user);
            result.put("data", 200);
            result.put("user", registeredUser);
            result.put("message", "微服务注册成功");
            result.put("source", "microservice");
        } catch (RuntimeException e) {
            result.put("data", 400);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "微服务注册失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 检查用户登录状态（微服务版本）
     * 与原系统路径 /checkmember 并行
     */
    @GetMapping("/check")
    public Map<String, Object> checkLogin(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        User sessionUser = (User) request.getSession().getAttribute("sessionmember");
        if (sessionUser != null) {
            User currentUser = userService.getUserById(sessionUser.getId());
            if (currentUser != null) {
                result.put("data", 200);
                result.put("sessionmember", currentUser);
                result.put("source", "microservice");
            } else {
                result.put("data", 400);
                result.put("message", "用户信息已失效");
            }
        } else {
            result.put("data", 400);
            result.put("message", "未登录");
        }
        
        return result;
    }
    
    /**
     * 用户登出（微服务版本）
     * 与原系统路径 /memberExit 并行
     */
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        request.getSession().removeAttribute("sessionmember");
        result.put("data", 200);
        result.put("message", "微服务登出成功");
        result.put("source", "microservice");
        
        return result;
    }
    
    /**
     * 检查用户名是否存在（微服务版本）
     * 与原系统路径 /checkUname 并行
     */
    @GetMapping("/check-username")
    public Map<String, Object> checkUsername(@RequestParam String uname) {
        Map<String, Object> result = new HashMap<>();
        
        boolean exists = userService.checkUsernameExists(uname);
        if (exists) {
            result.put("data", 400);
            result.put("message", "用户名已存在");
        } else {
            result.put("data", 200);
            result.put("message", "用户名可用");
        }
        result.put("source", "microservice");
        
        return result;
    }
    
    /**
     * 找回密码（微服务版本）
     * 与原系统路径 /forget 并行
     */
    @PostMapping("/forgot-password")
    public Map<String, Object> forgotPassword(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        String uname = params.get("uname");
        String tel = params.get("tel");
        
        try {
            String password = userService.getPasswordByUsernameAndPhone(uname, tel);
            if (password != null) {
                result.put("data", 200);
                result.put("mm", password);  // 兼容原系统字段名
                result.put("message", "微服务密码找回成功");
                result.put("source", "microservice");
            } else {
                result.put("data", 400);
                result.put("message", "用户名或手机号不匹配");
            }
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "微服务找回密码失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 后台用户列表（微服务版本）
     * 与原系统路径 /admin/memberList 并行
     */
    @GetMapping("/admin/list")
    public Map<String, Object> getUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String key) {
        
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        
        if (key != null && !key.trim().isEmpty()) {
            params.put("uname", key);
        }
        
        try {
            PageInfo<User> pageInfo = userService.getUserList(params, pageNum, pageSize);
            
            // 兼容原系统的返回格式
            result.put("pageInfo", pageInfo);
            result.put("list", pageInfo.getList());
            result.put("source", "microservice");
            
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "微服务获取用户列表失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 获取用户详细信息（微服务增强功能）
     */
    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                result.put("data", 200);
                result.put("user", user);
                result.put("source", "microservice");
            } else {
                result.put("data", 404);
                result.put("message", "用户不存在");
            }
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "获取用户信息失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 用户统计信息（微服务增强功能）
     */
    @GetMapping("/admin/stats")
    public Map<String, Object> getUserStats() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 总用户数
            long totalUsers = userService.getUserCount(new HashMap<>());
            
            // 活跃用户数
            Map<String, Object> activeParams = new HashMap<>();
            activeParams.put("status", "active");
            long activeUsers = userService.getUserCount(activeParams);
            
            // VIP用户数
            Map<String, Object> vipParams = new HashMap<>();
            vipParams.put("membershipType", "VIP");
            long vipUsers = userService.getUserCount(vipParams);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", totalUsers);
            stats.put("active", activeUsers);
            stats.put("vip", vipUsers);
            stats.put("inactive", totalUsers - activeUsers);
            stats.put("source", "microservice");
            
            result.put("data", 200);
            result.put("stats", stats);
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "获取统计信息失败: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 微服务 vs 原系统功能对比测试接口
     */
    @GetMapping("/compare-test")
    public Map<String, Object> compareTest() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取用户总数用于对比
            long userCount = userService.getUserCount(new HashMap<>());
            
            result.put("data", 200);
            result.put("message", "微服务功能正常");
            result.put("userCount", userCount);
            result.put("source", "microservice");
            result.put("version", "1.3.0-microservice");
            result.put("features", new String[]{
                "用户登录/注册", 
                "用户管理", 
                "会员系统", 
                "统计分析",
                "并行运行兼容"
            });
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "微服务测试失败: " + e.getMessage());
        }
        
        return result;
    }
}