package org.api.biuquiz.services;

import org.api.biuquiz.models.User;
import org.api.biuquiz.models.dto.RegisterRequest;
import org.api.biuquiz.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;


    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService, VerificationTokenService verificationTokenService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public void register(RegisterRequest request) {
        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("E-mail już zarejestrowany");
        }
        if (userRepo.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Nazwa użytkownika już zajęta");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmailVerified(false);
        user.setRole("USER");

        userRepo.save(user);

        var token = verificationTokenService.createToken(user);
        emailService.sendVerificationEmail(user.getEmail(), token.getToken());
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Nieprawidłowe dane logowania");
        }

        return jwtService.generateToken(user);
    }
}

