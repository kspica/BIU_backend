package org.api.biuquiz.services;

import jakarta.persistence.EntityNotFoundException;
import org.api.biuquiz.models.Question;
import org.api.biuquiz.models.Quiz;
import org.api.biuquiz.models.User;
import org.api.biuquiz.models.dto.CreateQuizRequest;
import org.api.biuquiz.repositories.QuizRepository;
import org.api.biuquiz.repositories.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.api.biuquiz.specifications.QuizSpecifications.*;

@Service
public class QuizService {

    private final QuizRepository quizRepo;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepo, UserRepository userRepository) {
        this.quizRepo = quizRepo;
        this.userRepository = userRepository;
    }

    public Quiz createQuiz(CreateQuizRequest dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setCategory(dto.getCategory());
        quiz.setDifficulty(dto.getDifficulty());
        quiz.setCoverImageUrl(dto.getCoverImageUrl());
        quiz.setTimeLimit(dto.getTimeLimit());

        List<Question> questions = dto.getQuestions().stream().map(q -> {
            Question question = new Question();
            question.setType(q.getType());
            question.setContent(q.getContent());
            question.setOptions(q.getOptions());
            question.setCorrectAnswers(q.getCorrectAnswers());
            question.setType(q.getType());
            question.setQuiz(quiz);
            return question;
        }).toList();

        quiz.setQuestions(questions);
        quiz.setCreatedBy(username);

        return quizRepo.save(quiz);
    }

    public List<Quiz> getQuizzesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));

        return quizRepo.findByCreatedBy(user.getUsername());
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }


    public Optional<Quiz> findByIdAndUsername(Long id, String username) {
        return quizRepo.findByIdAndCreatedBy(id, username);
    }

    public void deleteById(Long quizId) {
        quizRepo.deleteById(quizId);
    }


    public void updateQuiz(Long id, Quiz updatedData) {
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        quiz.setDescription(updatedData.getDescription());
        quiz.setCategory(updatedData.getCategory());
        quiz.setDifficulty(updatedData.getDifficulty());

        quiz.getQuestions().clear();
        for (Question question : updatedData.getQuestions()) {
            question.setQuiz(quiz);
            quiz.getQuestions().add(question);
        }

        quizRepo.save(quiz);
    }

    public List<Quiz> search(String title, String category, String difficulty, Integer timeLimit) {
        Specification<Quiz> spec = Specification
                .where(titleContains(title))
                .and(categoryEquals(category))
                .and(difficultyEquals(difficulty))
                .and(timeLimitEquals(timeLimit));

        return quizRepo.findAll(spec);
    }
}
