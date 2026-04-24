package com.it.movie.recommendation.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于内容的推荐算法
 * 根据电影的特征（类型、地区、演员等）计算电影间相似度
 */
public class ContentBasedAlgorithm {

    /**
     * 计算两部电影的余弦相似度
     * 基于电影的特征向量
     * @param movieFeatures1 电影1的特征向量
     * @param movieFeatures2 电影2的特征向量
     * @return 相似度分数 [0, 1]
     */
    public static double calculateMovieSimilarity(Map<String, Double> movieFeatures1, 
                                                   Map<String, Double> movieFeatures2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // 获取所有特征
        Map<String, Double> allFeatures = new HashMap<>(movieFeatures1);
        allFeatures.putAll(movieFeatures2);

        // 计算点积和模
        for (String feature : allFeatures.keySet()) {
            double value1 = movieFeatures1.getOrDefault(feature, 0.0);
            double value2 = movieFeatures2.getOrDefault(feature, 0.0);
            
            dotProduct += value1 * value2;
            normA += Math.pow(value1, 2);
            normB += Math.pow(value2, 2);
        }

        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * 基于用户观看历史计算用户对电影的兴趣度
     * @param userMovieFeatures 用户观看电影的平均特征
     * @param candidateMovieFeatures 候选电影特征
     * @return 兴趣度分数
     */
    public static double calculateUserInterest(Map<String, Double> userMovieFeatures,
                                               Map<String, Double> candidateMovieFeatures) {
        return calculateMovieSimilarity(userMovieFeatures, candidateMovieFeatures);
    }

    /**
     * 根据电影类别计算相似度
     * @param categories1 电影1的类别列表
     * @param categories2 电影2的类别列表
     * @return 相似度分数
     */
    public static double calculateCategorySimilarity(List<String> categories1, List<String> categories2) {
        if (categories1.isEmpty() || categories2.isEmpty()) {
            return 0.0;
        }

        int intersection = 0;
        for (String cat1 : categories1) {
            if (categories2.contains(cat1)) {
                intersection++;
            }
        }

        // Jaccard相似度：交集 / 并集
        int union = categories1.size() + categories2.size() - intersection;
        return (double) intersection / union;
    }

    /**
     * 根据地区计算相似度
     * @param area1 电影1的地区
     * @param area2 电影2的地区
     * @return 相似度分数
     */
    public static double calculateAreaSimilarity(String area1, String area2) {
        return area1 != null && area1.equals(area2) ? 1.0 : 0.0;
    }

    /**
     * 综合多个特征计算电影相似度
     * @param movieId1 电影ID1
     * @param movieId2 电影ID2
     * @param categoryWeight 类别权重
     * @param areaWeight 地区权重
     * @param featureWeight 特征权重
     * @return 综合相似度分数
     */
    public static double calculateCompositeSimilarity(
            Map<String, Double> features1, Map<String, Double> features2,
            List<String> categories1, List<String> categories2,
            String area1, String area2,
            double categoryWeight, double areaWeight, double featureWeight) {

        double categorySim = calculateCategorySimilarity(categories1, categories2);
        double areaSim = calculateAreaSimilarity(area1, area2);
        double featureSim = calculateMovieSimilarity(features1, features2);

        // 归一化权重
        double totalWeight = categoryWeight + areaWeight + featureWeight;
        
        return (categorySim * categoryWeight + 
                areaSim * areaWeight + 
                featureSim * featureWeight) / totalWeight;
    }
}