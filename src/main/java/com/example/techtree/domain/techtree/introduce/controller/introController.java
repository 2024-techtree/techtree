package com.example.techtree.domain.techtree.introduce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/introduce")
public class introController {
    @GetMapping
    public String introduce() {
        return "domain/introduce/introduce";
    }
}
