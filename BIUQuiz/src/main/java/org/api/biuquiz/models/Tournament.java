package org.api.biuquiz.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quizId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Tournament(Long quizId, LocalDateTime startTime, LocalDateTime endTime) {
        this.quizId = quizId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

