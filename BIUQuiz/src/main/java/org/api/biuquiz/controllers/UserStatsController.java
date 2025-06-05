package org.api.biuquiz.controllers;

import org.api.biuquiz.models.Quiz;
import org.api.biuquiz.models.QuizResult;
import org.api.biuquiz.models.User;
import org.api.biuquiz.models.dto.CategoryStatsDTO;
import org.api.biuquiz.models.dto.RecommendedQuizDTO;
import org.api.biuquiz.models.dto.UserStatsDTO;
import org.api.biuquiz.repositories.QuizRepository;
import org.api.biuquiz.repositories.QuizResultRepository;
import org.api.biuquiz.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class UserStatsController {
    private final QuizResultRepository resultRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public UserStatsController(QuizResultRepository resultRepository, UserRepository userRepository, QuizRepository quizRepository) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/summary")
    public ResponseEntity<UserStatsDTO> getUserStats(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        List<QuizResult> results = resultRepository.findByUser(user);

        int total = results.size();
        double avgScore = results.stream().mapToInt(QuizResult::getScore).average().orElse(0);
        int maxScore = results.stream().mapToInt(QuizResult::getScore).max().orElse(0);

        Map<String, Double> avgByCategory = results.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getQuiz().getCategory(),
                        Collectors.averagingInt(QuizResult::getScore)
                ));

        // Sortowanie kategorii rosnąco po średnim wyniku
        List<String> weakestCategories = avgByCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();

        List<Quiz> recommendedQuizzes = quizRepository.findTop3ByCategoryInOrderByIdDesc(weakestCategories);
        List<RecommendedQuizDTO> recommended = recommendedQuizzes.stream()
                .map(q -> new RecommendedQuizDTO(q.getId(), q.getTitle(), q.getCategory(), q.getDifficulty()))
                .toList();

        Map<String, Long> strengths = results.stream()
                .collect(Collectors.groupingBy(r -> r.getQuiz().getCategory(), Collectors.counting()));

        return ResponseEntity.ok(new UserStatsDTO(total, avgScore, maxScore, strengths, recommended));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryStatsDTO>> getCategoryStats(Principal principal) {
        return ResponseEntity.ok(resultRepository.countByCategoryForUser(principal.getName()));
    }


}

