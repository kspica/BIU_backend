package org.api.biuquiz.models.dto;

import lombok.Data;

@Data
public class SubmitResultRequest {
    private Long quizId;
    private int score;
    private int timeTakenSeconds;
}

