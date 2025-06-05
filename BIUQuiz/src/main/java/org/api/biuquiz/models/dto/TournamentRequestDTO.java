package org.api.biuquiz.models.dto;

import java.time.LocalDateTime;

public record TournamentRequestDTO(Long quizId, LocalDateTime startTime, LocalDateTime endTime) {
}

