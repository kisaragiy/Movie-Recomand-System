-- SQL Schema for Recommendation System

-- User Ratings table: stores the ratings given by users to movies
CREATE TABLE user_rating (
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating FLOAT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id)
);

-- Movie Features table: stores various features of each movie
CREATE TABLE movie_feature (
    movie_id INT NOT NULL,
    feature_name VARCHAR(255) NOT NULL,
    feature_value FLOAT NOT NULL,
    PRIMARY KEY (movie_id, feature_name)
);

-- Recommendation Log table: stores logs of recommendations provided to users
CREATE TABLE recommendation_log (
    user_id INT NOT NULL,
    recommended_movie_id INT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, recommended_movie_id)
);
