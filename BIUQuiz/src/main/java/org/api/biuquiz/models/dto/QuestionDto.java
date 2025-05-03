package org.api.biuquiz.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {
    private String type;
    private String content;
    private List<String> options;
    private List<String> correctAnswers;
}

