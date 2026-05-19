package com.it.movie.recommendation.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 基于用户的协同过滤（从初版 UserCFDemo 移植）
 */
public class UserCollaborativeFiltering {

    public String[] users;
    public String[] movies;
    public int[][] allUserMovieStarList;
    public int membernum;
    public int mvnum;

    private List<List<Object>> similarityUsers;
    private List<String> targetRecommendMovies;
    private List<String> commentedMovies;
    private Integer targetUserIndex;

    public List<String> recommendMovieIds(String userId) {
        List<String> result = new ArrayList<>();
        targetUserIndex = getUserIndex(userId);
        if (targetUserIndex == null) {
            return result;
        }
        calcUserSimilarity();
        calcRecommendMovie();
        handleRecommendMovies();
        if (targetRecommendMovies == null) {
            return result;
        }
        for (String item : targetRecommendMovies) {
            if (!commentedMovies.contains(item)) {
                result.add(item);
            }
        }
        targetRecommendMovies = null;
        return result;
    }

    private void handleRecommendMovies() {
        commentedMovies = new ArrayList<>();
        for (int i = 0; i < allUserMovieStarList[targetUserIndex].length; i++) {
            if (allUserMovieStarList[targetUserIndex][i] != 0) {
                commentedMovies.add(movies[i]);
            }
        }
    }

    private void calcRecommendMovie() {
        targetRecommendMovies = new ArrayList<>();
        List<List<Object>> recommendMovies = new ArrayList<>();
        double sumRate = 0;
        for (int i = 0; i < mvnum; i++) {
            List<Object> recommendMovie = new ArrayList<>();
            recommendMovie.add(i);
            double recommdRate = allUserMovieStarList[Integer.parseInt(similarityUsers.get(0).get(0).toString())][i]
                    * Double.parseDouble(similarityUsers.get(0).get(1).toString())
                    + allUserMovieStarList[Integer.parseInt(similarityUsers.get(1).get(0).toString())][i]
                    * Double.parseDouble(similarityUsers.get(1).get(1).toString());
            recommendMovie.add(recommdRate);
            recommendMovies.add(recommendMovie);
            sumRate += recommdRate;
        }
        sortCollection(recommendMovies, -1);
        for (List<Object> item : recommendMovies) {
            if (Double.parseDouble(item.get(1).toString()) > sumRate / mvnum) {
                targetRecommendMovies.add(movies[Integer.parseInt(item.get(0).toString())]);
            }
        }
    }

    private void calcUserSimilarity() {
        similarityUsers = new ArrayList<>();
        List<List<Object>> userSimilaritys = new ArrayList<>();
        for (int i = 0; i < membernum; i++) {
            if (i == targetUserIndex) {
                continue;
            }
            List<Object> userSimilarity = new ArrayList<>();
            userSimilarity.add(i);
            userSimilarity.add(calcTwoUserSimilarity(allUserMovieStarList[i], allUserMovieStarList[targetUserIndex]));
            userSimilaritys.add(userSimilarity);
        }
        sortCollection(userSimilaritys, 1);
        if (userSimilaritys.size() >= 2) {
            similarityUsers.add(userSimilaritys.get(0));
            similarityUsers.add(userSimilaritys.get(1));
        } else if (!userSimilaritys.isEmpty()) {
            similarityUsers.add(userSimilaritys.get(0));
            similarityUsers.add(userSimilaritys.get(0));
        }
    }

    private double calcTwoUserSimilarity(int[] user1Stars, int[] user2Starts) {
        float sum = 0;
        for (int i = 0; i < mvnum; i++) {
            sum += Math.pow(user1Stars[i] - user2Starts[i], 2);
        }
        return Math.sqrt(sum);
    }

    private Integer getUserIndex(String user) {
        if (user == null || user.isEmpty()) {
            return null;
        }
        for (int i = 0; i < users.length; i++) {
            if (user.equals(users[i])) {
                return i;
            }
        }
        return null;
    }

    private void sortCollection(List<List<Object>> list, final int order) {
        Collections.sort(list, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> o1, List<Object> o2) {
                double v1 = Double.parseDouble(o1.get(1).toString());
                double v2 = Double.parseDouble(o2.get(1).toString());
                if (v1 > v2) {
                    return order;
                } else if (v1 < v2) {
                    return -order;
                }
                return 0;
            }
        });
    }
}
