package org.api.biuquiz.controllers;

import org.api.biuquiz.models.Quiz;
import org.api.biuquiz.models.dto.CreateQuizRequest;
import org.api.biuquiz.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/user")
    public List<Quiz> getUserQuizzes(Principal principal) {
        return quizService.getQuizzesByUsername(principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id, Principal principal) {
        Optional<Quiz> quiz = quizService.findByIdAndUsername(id, principal.getName());
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }


    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody CreateQuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }
}

