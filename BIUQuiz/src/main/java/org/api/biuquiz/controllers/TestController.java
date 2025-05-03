package org.api.biuquiz.controllers;

import org.api.biuquiz.services.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final EmailService emailService;


    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Witaj, jesteś poprawnie uwierzytelniony!";
    }

    @GetMapping("/send")
    public void sendTestMail() {
        emailService.send("karolloczek@gmail.com", "Resetowanie hasła", "Kliknij link: ");
    }
}

