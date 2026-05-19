package com.it.movie.user.dao;

import com.it.movie.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    @Select({
            "<script>",
            "SELECT id, uname, upass, tname, filename, tel, sex, qq, email, isgly FROM member WHERE 1=1",
            "<if test='uname != null and uname != \"\"'> AND uname = #{uname}</if>",
            "<if test='key != null and key != \"\"'> AND uname LIKE CONCAT('%', #{key}, '%')</if>",
            "<if test='email != null and email != \"\"'> AND email = #{email}</if>",
            "<if test='tel != null and tel != \"\"'> AND tel = #{tel}</if>",
            "</script>"
    })
    List<User> selectAll(Map<String, Object> params);

    @Select("SELECT id, uname, upass, tname, filename, tel, sex, qq, email, isgly FROM member WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT id, uname, upass, tname, filename, tel, sex, qq, email, isgly FROM member WHERE uname = #{uname}")
    User findByUsername(@Param("uname") String uname);

    @Select("SELECT id, uname, upass, tname, filename, tel, sex, qq, email, isgly FROM member WHERE uname = #{uname} AND upass = #{upass}")
    User login(@Param("uname") String uname, @Param("upass") String upass);

    @Select("SELECT COUNT(*) FROM member WHERE uname = #{uname}")
    int checkUsernameExists(@Param("uname") String uname);

    @Select("SELECT COUNT(*) FROM member WHERE email = #{email}")
    int checkEmailExists(@Param("email") String email);

    @Insert("INSERT INTO member (uname, upass, tname, filename, tel, sex, qq, email, isgly) " +
            "VALUES (#{uname}, #{upass}, #{tname}, #{filename}, #{tel}, #{sex}, #{qq}, #{email}, 'n')")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(User user);

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
            "<if test='isgly != null'>isgly = #{isgly}</if>",
            " WHERE id = #{id}",
            "</script>"
    })
    int update(User user);

    @Delete("DELETE FROM member WHERE id = #{id}")
    int delete(@Param("id") Integer id);

    @Delete({
            "<script>",
            "DELETE FROM member WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach>",
            "</script>"
    })
    int batchDelete(@Param("ids") List<Integer> ids);

    @Select("SELECT COUNT(*) FROM member")
    long countUsers(Map<String, Object> params);
}
