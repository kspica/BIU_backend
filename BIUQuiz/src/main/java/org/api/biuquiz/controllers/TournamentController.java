package org.api.biuquiz.controllers;

import org.api.biuquiz.models.dto.TournamentRequestDTO;
import org.api.biuquiz.models.dto.TournamentResponseDTO;
import org.api.biuquiz.models.dto.TournamentResultDTO;
import org.api.biuquiz.services.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService service;

    public TournamentController(TournamentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TournamentRequestDTO dto) {
        service.createTournament(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<TournamentResponseDTO>> getActive() {
        return ResponseEntity.ok(service.getActiveTournaments());
    }

    @GetMapping("/{id}/leaderboard")
    public List<TournamentResultDTO> getLeaderboard(@PathVariable Long id) {
        return service.getLeaderboard(id);
    }

}

