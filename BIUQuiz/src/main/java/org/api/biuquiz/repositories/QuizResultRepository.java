package org.api.biuquiz.repositories;

import org.api.biuquiz.models.QuizResult;
import org.api.biuquiz.models.User;
import org.api.biuquiz.models.dto.CategoryStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findTop10ByQuizIdOrderByScoreDescTimeTakenSecondsAsc(Long quizId);

    List<QuizResult> findByTournamentIdOrderByScoreDesc(Long tournamentId);

    List<QuizResult> findByUser(User user);

    @Query("SELECT new org.api.biuquiz.models.dto.CategoryStatsDTO(q.category, COUNT(qr)) " +
            "FROM QuizResult qr JOIN qr.quiz q " +
            "WHERE qr.user.username = :username " +
            "GROUP BY q.category")
    List<CategoryStatsDTO> countByCategoryForUser(@Param("username") String username);

}

