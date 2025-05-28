package org.api.biuquiz.repositories;

import org.api.biuquiz.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findTop10ByQuizIdOrderByScoreDescTimeTakenSecondsAsc(Long quizId);
}

