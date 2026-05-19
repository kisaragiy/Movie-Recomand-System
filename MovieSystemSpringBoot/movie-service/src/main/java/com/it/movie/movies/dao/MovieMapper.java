package com.it.movie.movies.dao;

import com.it.movie.movies.entity.Area;
import com.it.movie.movies.entity.Category;
import com.it.movie.movies.entity.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MovieMapper {

    @Select({
            "<script>",
            "SELECT * FROM movie WHERE 1=1",
            "<if test='key != null and key != \"\"'>",
            " AND (name LIKE CONCAT('%',#{key},'%') OR author LIKE CONCAT('%',#{key},'%'))",
            "</if>",
            "<if test='key1 != null and key1 != \"\"'> AND categoryid = #{key1}</if>",
            "<if test='key2 != null and key2 != \"\"'> AND areaid = #{key2}</if>",
            "<if test='shstatus != null and shstatus != \"\"'> AND shstatus = #{shstatus}</if>",
            "<if test='memberid != null and memberid != \"\"'> AND memberid = #{memberid}</if>",
            "<choose>",
            "<when test='orderby == \"score\"'> ORDER BY score DESC</when>",
            "<when test='orderby == \"cs\"'> ORDER BY cs DESC</when>",
            "<otherwise> ORDER BY id DESC</otherwise>",
            "</choose>",
            "</script>"
    })
    List<Movie> selectAll(Map<String, Object> params);

    @Select("SELECT * FROM movie WHERE id = #{id}")
    Movie findById(@Param("id") Integer id);

    @Insert("INSERT INTO movie (name,filename,videoname,categoryid,areaid,author,yeartime,playtime,content,cs,score,isfree,fee,zan,memberid,shstatus) " +
            "VALUES (#{name},#{filename},#{videoname},#{categoryid},#{areaid},#{author},#{yeartime},#{playtime},#{content},#{cs},#{score},#{isfree},#{fee},0,#{memberid},#{shstatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(Movie movie);

    @Update({
            "<script>",
            "UPDATE movie SET",
            "<if test='name != null'>name=#{name},</if>",
            "<if test='filename != null'>filename=#{filename},</if>",
            "<if test='videoname != null'>videoname=#{videoname},</if>",
            "<if test='categoryid != null'>categoryid=#{categoryid},</if>",
            "<if test='areaid != null'>areaid=#{areaid},</if>",
            "<if test='author != null'>author=#{author},</if>",
            "<if test='yeartime != null'>yeartime=#{yeartime},</if>",
            "<if test='playtime != null'>playtime=#{playtime},</if>",
            "<if test='content != null'>content=#{content},</if>",
            "<if test='cs != null'>cs=#{cs},</if>",
            "<if test='score != null'>score=#{score},</if>",
            "<if test='isfree != null'>isfree=#{isfree},</if>",
            "<if test='fee != null'>fee=#{fee},</if>",
            "<if test='shstatus != null'>shstatus=#{shstatus}</if>",
            " WHERE id=#{id}",
            "</script>"
    })
    int update(Movie movie);

    @Delete("DELETE FROM movie WHERE id = #{id}")
    int delete(@Param("id") Integer id);

    @Select("SELECT id, name FROM category WHERE id = #{id}")
    Category findCategoryById(@Param("id") Integer id);

    @Select("SELECT id, name FROM area WHERE id = #{id}")
    Area findAreaById(@Param("id") Integer id);
}
