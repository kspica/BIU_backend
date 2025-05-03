package org.api.biuquiz.services;

import org.api.biuquiz.models.User;
import org.api.biuquiz.models.VerificationToken;
import org.api.biuquiz.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository repo;

    public VerificationTokenService(VerificationTokenRepository repo) {
        this.repo = repo;
    }

    public VerificationToken createToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        return repo.save(token);
    }

    public Optional<User> validateToken(String token) {
        return repo.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(VerificationToken::getUser);
    }
}

