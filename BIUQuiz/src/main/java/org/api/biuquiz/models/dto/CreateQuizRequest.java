package org.api.biuquiz.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateQuizRequest {
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private String coverImageUrl;
    private Integer timeLimit;
    private List<QuestionDto> questions;
}