package com.it.movie.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 用户实体类
 * 微服务架构下的用户数据模型
 */
public class User {
    
    private Integer id;
    private String uname;         // 用户名
    private String upass;         // 密码
    private String tname;         // 真实姓名
    private String filename;      // 头像文件名
    private String tel;           // 电话
    private String sex;           // 性别
    private String qq;            // QQ号
    private String email;         // 邮箱
    private String isgly;         // 是否管理员
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addtime;         // 注册时间
    
    // 扩展会员功能字段
    private String membershipType;     // 会员类型（普通/VIP/SVIP）
    private Date membershipExpire;     // 会员到期时间
    private Boolean autoRenewal;       // 自动续费
    private String status;             // 用户状态（active/inactive/banned）
    private Integer level;             // 用户等级
    private String avatar;             // 用户头像URL
    private Boolean membershipActive;  // 会员是否激活
    
    // 构造函数
    public User() {}
    
    public User(String uname, String upass, String email) {
        this.uname = uname;
        this.upass = upass;
        this.email = email;
        this.addtime = new Date();
        this.status = "active";
        this.membershipType = "普通";
        this.membershipActive = false;
        this.level = 1;
    }
    
    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUname() {
        return uname;
    }
    
    public void setUname(String uname) {
        this.uname = uname;
    }
    
    public String getUpass() {
        return upass;
    }
    
    public void setUpass(String upass) {
        this.upass = upass;
    }
    
    // 兼容性方法
    public String getPassword() {
        return upass;
    }
    
    public void setPassword(String password) {
        this.upass = password;
    }
    
    public String getUsername() {
        return uname;
    }
    
    public void setUsername(String username) {
        this.uname = username;
    }
    
    public String getTname() {
        return tname;
    }
    
    public void setTname(String tname) {
        this.tname = tname;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getQq() {
        return qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getIsgly() {
        return isgly;
    }
    
    public void setIsgly(String isgly) {
        this.isgly = isgly;
    }
    
    public Date getAddtime() {
        return addtime;
    }
    
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
    
    public String getMembershipType() {
        return membershipType;
    }
    
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
    
    public Date getMembershipExpire() {
        return membershipExpire;
    }
    
    public void setMembershipExpire(Date membershipExpire) {
        this.membershipExpire = membershipExpire;
    }
    
    public Boolean getAutoRenewal() {
        return autoRenewal;
    }
    
    public void setAutoRenewal(Boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Boolean getMembershipActive() {
        return membershipActive;
    }
    
    public void setMembershipActive(Boolean membershipActive) {
        this.membershipActive = membershipActive;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", tname='" + tname + '\'' +
                ", email='" + email + '\'' +
                ", membershipType='" + membershipType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}