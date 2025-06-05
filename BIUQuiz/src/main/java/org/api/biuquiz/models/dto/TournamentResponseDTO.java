package org.api.biuquiz.models.dto;

import java.time.LocalDateTime;

public record TournamentResponseDTO(Long id, Long quizId, String quizTitle, LocalDateTime startTime,
                                    LocalDateTime endTime) {
}

