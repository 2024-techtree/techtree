package com.example.techtree.domain.financial.target.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long target_id;

    //저축 목표 이름
    @Column(columnDefinition = "TEXT")
    private String targetName;

    //저축 목표 금액
    @Column
    private Long targetPrice;
    //저축 현재 금액
    @Column
    private Long currentPrice;

    //저축 목표 유형
    @Column
    private String targetType;

    //시작 날짜
    @Column
    private LocalDateTime startDate;

    //끝나는 날짜
    @Column
    private LocalDateTime endDate;

    //수정한 날짜
    @Column
    private LocalDateTime updateDate;

}
