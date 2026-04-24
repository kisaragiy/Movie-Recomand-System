import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HybridRecommendationAlgorithm {
    private CollaborativeFiltering collaborativeFiltering;
    private ContentBasedFiltering contentBasedFiltering;

    public HybridRecommendationAlgorithm(CollaborativeFiltering collaborativeFiltering, ContentBasedFiltering contentBasedFiltering) {
        this.collaborativeFiltering = collaborativeFiltering;
        this.contentBasedFiltering = contentBasedFiltering;
    }

    public List<Movie> recommendMovies(User user) {
        List<Movie> collaborativeRecommendations = collaborativeFiltering.getRecommendations(user);
        List<Movie> contentRecommendations = contentBasedFiltering.getRecommendations(user);

        return combineRecommendations(collaborativeRecommendations, contentRecommendations);
    }

    private List<Movie> combineRecommendations(List<Movie> collaborative, List<Movie> content) {
        Map<Movie, Double> combinedScores = new HashMap<>();

        for (Movie movie : collaborative) {
            combinedScores.put(movie, combinedScores.getOrDefault(movie, 0.0) + 1.0); // Weight: 1.0
        }

        for (Movie movie : content) {
            combinedScores.put(movie, combinedScores.getOrDefault(movie, 0.0) + 0.5); // Weight: 0.5
        }

        return combinedScores.keySet().stream()  
            .sorted((m1, m2) -> Double.compare(combinedScores.get(m2), combinedScores.get(m1)))
            .toList(); // Sorted list of movies
    }
}