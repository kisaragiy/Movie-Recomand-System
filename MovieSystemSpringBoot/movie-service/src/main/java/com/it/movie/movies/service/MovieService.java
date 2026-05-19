package com.it.movie.movies.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.movie.movies.dao.MovieMapper;
import com.it.movie.movies.entity.Area;
import com.it.movie.movies.entity.Category;
import com.it.movie.movies.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Cacheable(value = "movieList", key = "#params.toString() + '-' + #pageNum + '-' + #pageSize")
    public Map<String, Object> getMovieList(Map<String, Object> params, int pageNum, int pageSize) {
        List<Movie> all = movieMapper.selectAll(params);
        enrichMovies(all);
        PageHelper.startPage(pageNum, pageSize);
        List<Movie> page = movieMapper.selectAll(params);
        enrichMovies(page);
        PageInfo<Movie> pageInfo = new PageInfo<>(page);

        Map<String, Object> res = new HashMap<>();
        res.put("pageInfo", pageInfo);
        res.put("list", all);
        return res;
    }

    public Movie getById(Integer id) {
        Movie movie = movieMapper.findById(id);
        if (movie != null) {
            enrichMovie(movie);
        }
        return movie;
    }

    @CacheEvict(value = "movieList", allEntries = true)
    public void add(Movie movie) {
        if (movie.getShstatus() == null) {
            movie.setShstatus("通过");
        }
        movieMapper.add(movie);
    }

    @CacheEvict(value = "movieList", allEntries = true)
    public void update(Movie movie) {
        movieMapper.update(movie);
    }

    @CacheEvict(value = "movieList", allEntries = true)
    public void delete(Integer id) {
        movieMapper.delete(id);
    }

    private void enrichMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            enrichMovie(movie);
        }
    }

    private void enrichMovie(Movie movie) {
        Category category = movieMapper.findCategoryById(movie.getCategoryid());
        Area area = movieMapper.findAreaById(movie.getAreaid());
        movie.setCategory(category);
        movie.setArea(area);
    }
}
