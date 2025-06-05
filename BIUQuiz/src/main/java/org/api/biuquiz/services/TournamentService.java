package org.api.biuquiz.services;

import org.api.biuquiz.models.Quiz;
import org.api.biuquiz.models.Tournament;
import org.api.biuquiz.models.dto.TournamentRequestDTO;
import org.api.biuquiz.models.dto.TournamentResponseDTO;
import org.api.biuquiz.models.dto.TournamentResultDTO;
import org.api.biuquiz.repositories.QuizRepository;
import org.api.biuquiz.repositories.QuizResultRepository;
import org.api.biuquiz.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;


    public TournamentService(TournamentRepository tournamentRepository, QuizRepository quizRepository, QuizResultRepository quizResultRepository) {
        this.tournamentRepository = tournamentRepository;
        this.quizRepository = quizRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public void createTournament(TournamentRequestDTO dto) {
        Tournament t = new Tournament(dto.quizId(), dto.startTime(), dto.endTime());
        tournamentRepository.save(t);
    }

    public List<TournamentResultDTO> getLeaderboard(Long tournamentId) {
        return quizResultRepository.findByTournamentIdOrderByScoreDesc(tournamentId)
                .stream()
                .map(r -> new TournamentResultDTO(r.getUser().getUsername(), r.getScore()))
                .toList();
    }

    public List<TournamentResponseDTO> getActiveTournaments() {
        LocalDateTime now = LocalDateTime.now();
        return tournamentRepository.findByStartTimeBeforeAndEndTimeAfter(now, now)
                .stream()
                .map(t -> {
                    Quiz quiz = quizRepository.findById(t.getQuizId()).orElse(null);
                    String title = (quiz != null) ? quiz.getTitle() : "Nieznany Quiz";
                    return new TournamentResponseDTO(t.getId(), t.getQuizId(), title, t.getStartTime(), t.getEndTime());
                })
                .collect(Collectors.toList());
    }
}

