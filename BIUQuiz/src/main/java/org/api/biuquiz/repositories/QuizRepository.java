package org.api.biuquiz.repositories;

import org.api.biuquiz.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreatedBy(String username);
    Optional<Quiz> findByIdAndCreatedBy(Long id, String username);
    void deleteById(Long id);
}
