package com.it.movie.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "movie-service", fallback = MovieServiceFallback.class)
public interface MovieServiceClient {

    @GetMapping("/api/movies/{movieId}")
    Object getMovieById(@PathVariable("movieId") Long movieId);

    @GetMapping("/api/movies")
    List<Object> getMoviesByCategory(@RequestParam("category") String category);

    @GetMapping("/api/movies/popular")
    List<Object> getPopularMovies(@RequestParam(value = "limit", defaultValue = "10") int limit);
}

class MovieServiceFallback implements MovieServiceClient {

    @Override
    public Object getMovieById(Long movieId) {
        return "Movie service is temporarily unavailable";
    }

    @Override
    public List<Object> getMoviesByCategory(String category) {
        return List.of("Movie category service is temporarily unavailable");
    }

    @Override
    public List<Object> getPopularMovies(int limit) {
        return List.of("Popular movies service is temporarily unavailable");
    }
}