package org.api.biuquiz.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserStatsDTO {
    private int totalQuizzes;
    private double averageScore;
    private int highestScore;
    private Map<String, Long> categoryStats;
    private List<RecommendedQuizDTO> recommended;

}
