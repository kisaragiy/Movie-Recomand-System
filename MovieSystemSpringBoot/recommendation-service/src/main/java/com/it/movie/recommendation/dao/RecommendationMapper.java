package com.it.movie.recommendation.dao;

import com.it.movie.recommendation.entity.Area;
import com.it.movie.recommendation.entity.Category;
import com.it.movie.recommendation.entity.Movie;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendationMapper {

    @Select("SELECT id FROM member")
    List<Integer> selectAllMemberIds();

    @Select("SELECT id, name, filename, categoryid, areaid, shstatus FROM movie WHERE shstatus = '通过'")
    List<Movie> selectApprovedMovies();

    @Select("SELECT id, name, filename, categoryid, areaid, shstatus FROM movie")
    List<Movie> selectAllMovies();

    @Select("SELECT id, name FROM category WHERE id = #{id}")
    Category findCategoryById(@Param("id") Integer id);

    @Select("SELECT id, name FROM area WHERE id = #{id}")
    Area findAreaById(@Param("id") Integer id);

    @Select("SELECT id, name, filename, categoryid, areaid, shstatus FROM movie WHERE id = #{id}")
    Movie findMovieById(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM collect WHERE movieid = #{movieid} AND memberid = #{memberid}")
    int countCollect(@Param("movieid") Integer movieid, @Param("memberid") Integer memberid);

    @Select("SELECT COALESCE(SUM(score), 0) FROM comment WHERE movieid = #{movieid} AND memberid = #{memberid}")
    int sumCommentScore(@Param("movieid") Integer movieid, @Param("memberid") Integer memberid);

    @Select("SELECT COUNT(*) FROM playrecord WHERE movieid = #{movieid} AND memberid = #{memberid}")
    int countPlayrecord(@Param("movieid") Integer movieid, @Param("memberid") Integer memberid);

    @Select("SELECT movieid FROM playrecord WHERE memberid = #{memberid}")
    List<Integer> selectPlayedMovieIds(@Param("memberid") Integer memberid);
}
