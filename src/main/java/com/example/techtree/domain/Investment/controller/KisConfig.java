package com.example.techtree.domain.Investment.controller;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KisConfig {
    public static final String REST_BASE_URL =
            "https://openapi.koreainvestment.com:9443";
    public static final String WS_BASE_URL =
            "ws://ops.koreainvestment.com:29443";
    public static final String APPKEY =
            "PS0Y0uetLceTG2vmlpnqoQ2uOsZXWlQCjtMQ";       // your APPKEY
    public static final String APPSECRET = "5RUv0SAabbiIiaWVBhm0SDhJw1/NJG48GmkfyrN8u2XWMquOa92D4+uoMWPXpmDpLaWFDidKrlxAg9b/MBo+Z60lDGI6ZC33gGeMrwNXckG+/zyl66BMaf4exhJ3vn/gafaNva6rwpGuTxZGxaudYXIn/JRbhYPyPDfYcTYaA3LuR5ejhQM=";  // your APPSECRET

    public static final String FHKUP03500100_PATH = "/uapi/domestic-stock/v1/quotations/inquire-daily-indexchartprice";
    public static final String FHKST03030100_PATH = "/uapi/overseas-price/v1/quotations/inquire-daily-chartprice";
}
