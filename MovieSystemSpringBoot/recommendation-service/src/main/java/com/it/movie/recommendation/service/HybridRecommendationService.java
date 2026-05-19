package com.it.movie.recommendation.service;

import com.it.movie.recommendation.algorithm.UserCollaborativeFiltering;
import com.it.movie.recommendation.dao.RecommendationMapper;
import com.it.movie.recommendation.entity.Area;
import com.it.movie.recommendation.entity.Category;
import com.it.movie.recommendation.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 混合推荐：协同过滤 + 基于电影标签（分类/地区）的内容推荐
 * 逻辑与初版 MovieController 保持一致
 */
@Service
public class HybridRecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;

    @Cacheable(value = "userRecommendations", key = "#userId")
    public List<Integer> getHybridRecommendationIds(Integer userId) {
        List<Integer> cfResult = collaborativeFilteringIds(userId);
        List<Integer> cbResult = contentBasedIds(userId);

        List<Integer> hybrid = new ArrayList<>();
        if (cfResult.isEmpty()) {
            if (!cbResult.isEmpty()) {
                hybrid.addAll(cbResult.subList(0, Math.min(5, cbResult.size())));
            }
        } else {
            hybrid.addAll(cfResult.subList(0, Math.min(5, cfResult.size())));
        }
        if (!cbResult.isEmpty() && hybrid.size() < 10) {
            for (Integer id : cbResult) {
                if (!hybrid.contains(id) && hybrid.size() < 10) {
                    hybrid.add(id);
                }
            }
        }
        return hybrid;
    }

    /** 猜你喜欢：协同过滤（与初版 loveRecommend 评分矩阵一致，不做归一化） */
    public List<Movie> getLoveRecommendMovies(Integer userId) {
        List<String> movieIdStrs = collaborativeFilteringMovieIdsRaw(userId);
        List<Movie> movies = new ArrayList<>();
        for (String mid : movieIdStrs) {
            Movie movie = recommendationMapper.findMovieById(Integer.valueOf(mid));
            if (movie != null) {
                enrichMovie(movie);
                movies.add(movie);
            }
        }
        return movies;
    }

    public List<Movie> getMoviesByIds(List<Integer> ids) {
        List<Movie> movies = new ArrayList<>();
        for (Integer id : ids) {
            Movie movie = recommendationMapper.findMovieById(id);
            if (movie != null) {
                enrichMovie(movie);
                movies.add(movie);
            }
        }
        return movies;
    }

    private List<Integer> collaborativeFilteringIds(Integer userId) {
        return collaborativeFilteringMovieIds(userId).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private List<String> collaborativeFilteringMovieIds(Integer userId) {
        return runCollaborativeFiltering(userId, true);
    }

    private List<String> collaborativeFilteringMovieIdsRaw(Integer userId) {
        return runCollaborativeFiltering(userId, false);
    }

    private List<String> runCollaborativeFiltering(Integer userId, boolean normalize) {
        List<Integer> memberIds = recommendationMapper.selectAllMemberIds();
        List<Movie> movies = recommendationMapper.selectApprovedMovies();
        if (memberIds.isEmpty() || movies.isEmpty()) {
            return Collections.emptyList();
        }

        String[] userArray = memberIds.stream().map(String::valueOf).toArray(String[]::new);
        String[] movieArray = movies.stream().map(m -> String.valueOf(m.getId())).toArray(String[]::new);
        int[][] matrix = buildUserMovieMatrix(memberIds, movies, normalize);

        UserCollaborativeFiltering cf = new UserCollaborativeFiltering();
        cf.users = userArray;
        cf.movies = movieArray;
        cf.allUserMovieStarList = matrix;
        cf.membernum = memberIds.size();
        cf.mvnum = movies.size();

        return cf.recommendMovieIds(String.valueOf(userId));
    }

    private int[][] buildUserMovieMatrix(List<Integer> memberIds, List<Movie> movies, boolean normalize) {
        int[][] matrix = new int[memberIds.size()][movies.size()];
        for (int i = 0; i < memberIds.size(); i++) {
            Integer memberId = memberIds.get(i);
            for (int j = 0; j < movies.size(); j++) {
                Integer movieId = movies.get(j).getId();
                int pf = 0;
                pf += recommendationMapper.countCollect(movieId, memberId) * 2;
                pf += recommendationMapper.sumCommentScore(movieId, memberId);
                pf += recommendationMapper.countPlayrecord(movieId, memberId);
                if (normalize) {
                    int maxPf = 70;
                    matrix[i][j] = Math.min((int) (pf * 100.0 / maxPf), 100);
                } else {
                    matrix[i][j] = pf;
                }
            }
        }
        return matrix;
    }

    /**
     * 内容推荐：基于用户观影记录提取分类/地区标签，匹配电影特征
     */
    public List<Integer> contentBasedIds(Integer userId) {
        List<Integer> playedIds = recommendationMapper.selectPlayedMovieIds(userId);
        Set<String> userInterests = new HashSet<>();
        for (Integer movieId : playedIds) {
            Movie movie = recommendationMapper.findMovieById(movieId);
            if (movie == null) {
                continue;
            }
            Category category = recommendationMapper.findCategoryById(movie.getCategoryid());
            Area area = recommendationMapper.findAreaById(movie.getAreaid());
            if (category != null) {
                userInterests.add(category.getName());
            }
            if (area != null) {
                userInterests.add(area.getName());
            }
            if (userInterests.size() >= 10) {
                break;
            }
        }

        List<Movie> allMovies = recommendationMapper.selectAllMovies();
        Map<Integer, Set<String>> movieFeatures = new HashMap<>();
        for (Movie movie : allMovies) {
            Category category = recommendationMapper.findCategoryById(movie.getCategoryid());
            Area area = recommendationMapper.findAreaById(movie.getAreaid());
            Set<String> features = new HashSet<>();
            if (category != null) {
                features.add(category.getName());
            }
            if (area != null) {
                features.add(area.getName());
            }
            movieFeatures.put(movie.getId(), features);
        }

        final Set<String> interests = userInterests;
        return movieFeatures.entrySet().stream()
                .sorted((e1, e2) -> {
                    long match1 = e1.getValue().stream().filter(interests::contains).count();
                    long match2 = e2.getValue().stream().filter(interests::contains).count();
                    return Long.compare(match2, match1);
                })
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }

    private void enrichMovie(Movie movie) {
        Category category = recommendationMapper.findCategoryById(movie.getCategoryid());
        Area area = recommendationMapper.findAreaById(movie.getAreaid());
        movie.setCategory(category);
        movie.setArea(area);
    }
}
