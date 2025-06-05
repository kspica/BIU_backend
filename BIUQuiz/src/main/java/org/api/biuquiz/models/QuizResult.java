package org.api.biuquiz.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private int timeTakenSeconds;

    private LocalDateTime completedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Quiz quiz;

    private Long tournamentId;
}

