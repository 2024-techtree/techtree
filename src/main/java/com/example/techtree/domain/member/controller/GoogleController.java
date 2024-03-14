package com.example.techtree.domain.member.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleController {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("googleClientId", googleClientId);
        return "domain/member/login_form";
    }
}
