package org.api.biuquiz.controllers;

import org.api.biuquiz.models.dto.AuthRequest;
import org.api.biuquiz.models.dto.RegisterRequest;
import org.api.biuquiz.repositories.UserRepository;
import org.api.biuquiz.services.AuthService;
import org.api.biuquiz.services.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, VerificationTokenService verificationTokenService, UserRepository userRepository) {
        this.authService = authService;
        this.verificationTokenService = verificationTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("Sprawdź skrzynkę e-mail");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest req) {
        String token = authService.login(req.username(), req.password());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        return verificationTokenService.validateToken(token)
                .map(user -> {
                    user.setEmailVerified(true);
                    userRepository.save(user);
                    return ResponseEntity.ok("Email zweryfikowany");
                }).orElse(ResponseEntity.badRequest().body("Nieprawidłowy token"));
    }

    @GetMapping("/check-username")
    public ResponseEntity<Void> checkUsername(@RequestParam String username) {
        return userRepository.findByUsername(username).isPresent()
                ? ResponseEntity.status(HttpStatus.CONFLICT).build()
                : ResponseEntity.ok().build();
    }

    @GetMapping("/check-email")
    public ResponseEntity<Void> checkEmail(@RequestParam String email) {
        return userRepository.findByEmail(email).isPresent()
                ? ResponseEntity.status(HttpStatus.CONFLICT).build()
                : ResponseEntity.ok().build();
    }
}

