package com.example.techtree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // 추후에 삭제 예정 (엔터티 어노테이션 용도)
public class TechtreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechtreeApplication.class, args);
    }

}
