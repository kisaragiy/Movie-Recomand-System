package com.it.movie.user.dao;

import com.it.movie.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户数据访问层
 * 使用MyBatis注解方式，支持微服务独立数据库
 */
@Repository
@Mapper
public interface UserMapper {
    
    /**
     * 查询所有用户（支持条件筛选）
     */
    @Select({
        "<script>",
        "SELECT * FROM member WHERE 1=1",
        "<if test='uname != null and uname != \"\"'>",
        "AND uname LIKE CONCAT('%', #{uname}, '%')",
        "</if>",
        "<if test='email != null and email != \"\"'>",
        "AND email = #{email}",
        "</if>",
        "<if test='tel != null and tel != \"\"'>",
        "AND tel = #{tel}",
        "</if>",
        "<if test='status != null and status != \"\"'>",
        "AND status = #{status}",
        "</if>",
        "<if test='membershipType != null and membershipType != \"\"'>",
        "AND membershipType = #{membershipType}",
        "</if>",
        "ORDER BY addtime DESC",
        "</script>"
    })
    List<User> selectAll(Map<String, Object> params);
    
    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM member WHERE id = #{id}")
    User findById(@Param("id") Integer id);
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM member WHERE uname = #{uname}")
    User findByUsername(@Param("uname") String uname);
    
    /**
     * 根据邮箱查询用户
     */
    @Select("SELECT * FROM member WHERE email = #{email}")
    User findByEmail(@Param("email") String email);
    
    /**
     * 用户登录验证
     */
    @Select("SELECT * FROM member WHERE uname = #{uname} AND upass = #{upass}")
    User login(@Param("uname") String uname, @Param("upass") String upass);
    
    /**
     * 检查用户名是否存在
     */
    @Select("SELECT COUNT(*) FROM member WHERE uname = #{uname}")
    int checkUsernameExists(@Param("uname") String uname);
    
    /**
     * 检查邮箱是否存在
     */
    @Select("SELECT COUNT(*) FROM member WHERE email = #{email}")
    int checkEmailExists(@Param("email") String email);
    
    /**
     * 添加用户
     */
    @Insert({
        "INSERT INTO member (uname, upass, tname, filename, tel, sex, qq, email, isgly, addtime,",
        "membershipType, membershipExpire, autoRenewal, status, level, avatar, membershipActive)",
        "VALUES (#{uname}, #{upass}, #{tname}, #{filename}, #{tel}, #{sex}, #{qq}, #{email},",
        "#{isgly}, #{addtime}, #{membershipType}, #{membershipExpire}, #{autoRenewal},", 
        "#{status}, #{level}, #{avatar}, #{membershipActive})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(User user);
    
    /**
     * 更新用户信息
     */
    @Update({
        "<script>",
        "UPDATE member SET",
        "<if test='uname != null'>uname = #{uname},</if>",
        "<if test='upass != null'>upass = #{upass},</if>",
        "<if test='tname != null'>tname = #{tname},</if>",
        "<if test='filename != null'>filename = #{filename},</if>",
        "<if test='tel != null'>tel = #{tel},</if>",
        "<if test='sex != null'>sex = #{sex},</if>",
        "<if test='qq != null'>qq = #{qq},</if>",
        "<if test='email != null'>email = #{email},</if>",
        "<if test='isgly != null'>isgly = #{isgly},</if>",
        "<if test='membershipType != null'>membershipType = #{membershipType},</if>",
        "<if test='membershipExpire != null'>membershipExpire = #{membershipExpire},</if>",
        "<if test='autoRenewal != null'>autoRenewal = #{autoRenewal},</if>",
        "<if test='status != null'>status = #{status},</if>",
        "<if test='level != null'>level = #{level},</if>",
        "<if test='avatar != null'>avatar = #{avatar},</if>",
        "<if test='membershipActive != null'>membershipActive = #{membershipActive}</if>",
        "WHERE id = #{id}",
        "</script>"
    })
    int update(User user);
    
    /**
     * 删除用户
     */
    @Delete("DELETE FROM member WHERE id = #{id}")
    int delete(@Param("id") Integer id);
    
    /**
     * 批量删除用户
     */
    @Delete({
        "<script>",
        "DELETE FROM member WHERE id IN",
        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</script>"
    })
    int batchDelete(@Param("ids") List<Integer> ids);
    
    /**
     * 统计用户数量
     */
    @Select({
        "<script>",
        "SELECT COUNT(*) FROM member WHERE 1=1",
        "<if test='status != null and status != \"\"'>",
        "AND status = #{status}",
        "</if>",
        "<if test='membershipType != null and membershipType != \"\"'>",
        "AND membershipType = #{membershipType}",
        "</if>",
        "</script>"
    })
    long countUsers(Map<String, Object> params);
    
    /**
     * 更新用户状态
     */
    @Update("UPDATE member SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
    
    /**
     * 升级用户会员
     */
    @Update("UPDATE member SET membershipType = #{membershipType}, " +
            "membershipExpire = #{membershipExpire}, membershipActive = true " +
            "WHERE id = #{id}")
    int upgradeMembership(@Param("id") Integer id, 
                         @Param("membershipType") String membershipType,
                         @Param("membershipExpire") java.util.Date membershipExpire);
}