package com.it.movie.movies.controller;

import com.it.movie.movies.entity.Movie;
import com.it.movie.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/microservice/api/movie")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class MovieMicroserviceController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("service", "movie-service");
        result.put("status", "UP");
        return result;
    }

    /** 后台电影列表，兼容初版 admin/movieList */
    @GetMapping("/admin/list")
    public Map<String, Object> getMovieList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String key1,
            @RequestParam(required = false) String key2,
            @RequestParam(required = false) String shstatus) {
        Map<String, Object> params = new HashMap<>();
        if (key != null) params.put("key", key);
        if (key1 != null) params.put("key1", key1);
        if (key2 != null) params.put("key2", key2);
        if (shstatus != null) params.put("shstatus", shstatus);
        Map<String, Object> result = movieService.getMovieList(params, pageNum, pageSize);
        result.put("source", "microservice");
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getMovie(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Movie movie = movieService.getById(id);
        result.put("movie", movie);
        result.put("data", movie != null ? 200 : 404);
        return result;
    }

    @PostMapping("/admin/add")
    public Map<String, Object> add(@RequestBody Movie movie) {
        movieService.add(movie);
        Map<String, Object> result = new HashMap<>();
        result.put("data", 200);
        return result;
    }

    @PutMapping("/admin/edit")
    public Map<String, Object> edit(@RequestBody Movie movie) {
        movieService.update(movie);
        Map<String, Object> result = new HashMap<>();
        result.put("data", 200);
        return result;
    }

    @DeleteMapping("/admin/delete")
    public Map<String, Object> delete(@RequestParam int id) {
        movieService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("data", 200);
        return result;
    }
}
