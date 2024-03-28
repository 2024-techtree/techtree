package com.example.techtree.domain.Investment.exchange.controller;

import com.example.techtree.domain.Investment.exchange.Dto.ExchangeDto;
import com.example.techtree.domain.Investment.exchange.service.ExchangeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExchangeController {

    private final ExchangeUtils exchangeUtils;

    public ExchangeController(ExchangeUtils exchangeUtils) {
        this.exchangeUtils = exchangeUtils;
    }

    @GetMapping("/exchange-rate")
    public String showExchangeRates(Model model) {
        List<ExchangeDto> exchangeDtoList = exchangeUtils.getExchangeDataAsDtoList();
        model.addAttribute("exchangeDtoList", exchangeDtoList);
        return "domain/investment/exchange-rates";
    }
}