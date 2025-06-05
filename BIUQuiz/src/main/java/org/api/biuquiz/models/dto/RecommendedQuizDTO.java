package org.api.biuquiz.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendedQuizDTO {
    private Long id;
    private String title;
    private String category;
    private String difficulty;
}
