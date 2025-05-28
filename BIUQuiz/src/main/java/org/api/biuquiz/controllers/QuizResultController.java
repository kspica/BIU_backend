package org.api.biuquiz.controllers;

import org.api.biuquiz.models.QuizResult;
import org.api.biuquiz.models.User;
import org.api.biuquiz.models.Quiz;
import org.api.biuquiz.models.dto.SubmitResultRequest;
import org.api.biuquiz.repositories.QuizRepository;
import org.api.biuquiz.repositories.QuizResultRepository;
import org.api.biuquiz.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class QuizResultController {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizResultRepository resultRepository;

    public QuizResultController(QuizRepository quizRepository, UserRepository userRepository, QuizResultRepository resultRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    @GetMapping("/top10/{quizId}")
    public ResponseEntity<List<QuizResult>> getTop10Results(@PathVariable Long quizId) {
        List<QuizResult> results = resultRepository.findTop10ByQuizIdOrderByScoreDescTimeTakenSecondsAsc(quizId);
        return ResponseEntity.ok(results);
    }


    @PostMapping
    public ResponseEntity<?> submitResult(@RequestBody SubmitResultRequest request, Principal principal) {
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElseThrow();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        QuizResult result = QuizResult.builder()
                .quiz(quiz)
                .user(user)
                .score(request.getScore())
                .timeTakenSeconds(request.getTimeTakenSeconds())
                .completedAt(LocalDateTime.now())
                .build();

        resultRepository.save(result);
        return ResponseEntity.ok().build();
    }
}
