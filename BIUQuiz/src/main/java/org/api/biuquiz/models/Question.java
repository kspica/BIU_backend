package org.api.biuquiz.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String content;

    @ElementCollection
    private List<String> options;

    @ElementCollection
    private List<String> correctAnswers;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}

