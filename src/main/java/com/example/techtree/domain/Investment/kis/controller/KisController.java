package com.example.techtree.domain.Investment.kis.controller;


import com.example.techtree.domain.Investment.kis.model.IndexData;
import com.example.techtree.domain.Investment.kis.model.Invesment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/investment")
public class KisController {
    @Autowired
    private AccessTokenManager accessTokenManager;

    private final WebClient webClient;
    private String path;
    private String tr_id;

    public KisController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(KisConfig.REST_BASE_URL).build();
    }

    @GetMapping("/indices")
    public String majorIndices(Model model) {

        List<Tuple2<String, String>> iscdsAndOtherVariable1 = Arrays.asList(
                Tuples.of("0001", "U"),
                Tuples.of("2001", "U"),
                Tuples.of("1001", "U")
        );
        List<Tuple2<String, String>> iscdsAndOtherVariable2 = Arrays.asList(
                Tuples.of(".DJI", "N"),
                Tuples.of("COMP", "N"),
                Tuples.of("SX5E", "N"), // 유로
                Tuples.of("JP#NI225", "N"), // 닛케이
                Tuples.of("CZ#399102", "N"),  // 심천
                Tuples.of("HSCE", "N")  // 홍콩 항셍지수
        );
        System.out.println("iscdsAndOtherVariable1 = " + iscdsAndOtherVariable1);
        System.out.println("iscdsAndOtherVariable2 = " + iscdsAndOtherVariable2);

        Flux<IndexData> indicesFlux = Flux.fromIterable(iscdsAndOtherVariable1)
                .concatMap(tuple -> getMajorIndex(tuple.getT1(), tuple.getT2()))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
        Flux<IndexData> indicesFlux1 = Flux.fromIterable(iscdsAndOtherVariable2)
                .concatMap(tuple -> getMajorIndex(tuple.getT1(), tuple.getT2()))
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(jsonData, IndexData.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println("indicesFlux1 = " + indicesFlux1);

        List<IndexData> indicesList = indicesFlux.collectList().block();
        List<IndexData> indicesList1 = indicesFlux1.collectList().block();
        model.addAttribute("indicesKor", indicesList);
        model.addAttribute("indicesOvr", indicesList1);

        model.addAttribute("jobDate", getJobDateTime());

        return "domain/investment/investmentTotal";
    }

    public String getStringToday() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return localDate.format(formatter);
    }

    public String getJobDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public Mono<String> getMajorIndex(String iscd, String fid_cond_mrkt_div_code) {

        if (fid_cond_mrkt_div_code.equals("U")) {
            path = KisConfig.FHKUP03500100_PATH;
            tr_id = "FHKUP03500100";
        } else {
            path = KisConfig.FHKST03030100_PATH;
            tr_id = "FHKST03030100";
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("fid_cond_mrkt_div_code", fid_cond_mrkt_div_code)
                        .queryParam("fid_input_iscd", iscd)
                        .queryParam("fid_input_date_1", getStringToday())
                        .queryParam("fid_input_date_2", getStringToday())
                        .queryParam("fid_period_div_code", "D")
                        .build())
                .header("content-type","application/json")
                .header("authorization","Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjI5NGI5NTU5LTkwOWUtNDc3YS1hMDVlLTY0NzU1OTI5ZWExNiIsImlzcyI6InVub2d3IiwiZXhwIjoxNzExNjg3OTA1LCJpYXQiOjE3MTE2MDE1MDUsImp0aSI6IlBTMHE5TDNUNW1YclRoNTJJc0lzQWJJNjZtQm1kUGg4M1lyYyJ9.Fi8p6-AfXQmaDWSy8biKKFYlniR0a7Um3yBXG6QyPjtzfMobczxDcfE9cyJrP2GsdHpumtGEApsSYQltvjzQRg")
                .header("appkey",KisConfig.APPKEY)
                .header("appsecret",KisConfig.APPSECRET)
                .header("tr_id",tr_id)
                .retrieve()
                .bodyToMono(String.class);

    }

    @GetMapping("/equities/{id}")
    public Mono<String> CurrentPrice(@PathVariable("id") String id, Model model) {
        String url = KisConfig.REST_BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-price?fid_cond_mrkt_div_code=J&fid_input_iscd=" + id;

        return webClient.get()
                .uri(url)
                .header("content-type","application/json")
                .header("authorization","Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjI5NGI5NTU5LTkwOWUtNDc3YS1hMDVlLTY0NzU1OTI5ZWExNiIsImlzcyI6InVub2d3IiwiZXhwIjoxNzExNjg3OTA1LCJpYXQiOjE3MTE2MDE1MDUsImp0aSI6IlBTMHE5TDNUNW1YclRoNTJJc0lzQWJJNjZtQm1kUGg4M1lyYyJ9.Fi8p6-AfXQmaDWSy8biKKFYlniR0a7Um3yBXG6QyPjtzfMobczxDcfE9cyJrP2GsdHpumtGEApsSYQltvjzQRg")
                .header("appkey",KisConfig.APPKEY)
                .header("appsecret",KisConfig.APPSECRET)
                .header("tr_id","FHKST01010100")
                .retrieve()
                .bodyToMono(Invesment.class)
                .doOnSuccess(body -> {
                    model.addAttribute("equity", body.getOutput());
                    model.addAttribute("jobDate", getJobDateTime());
                    model.addAttribute("id", id);

                })
                .doOnError(result -> System.out.println("*** error: " + result))
                .thenReturn("domain/investment/equities");
    }
}