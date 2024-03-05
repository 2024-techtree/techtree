package com.example.techtree.domain.saving.goal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saving_goal_id;

    //저축 목표 이름
    @Column(columnDefinition = "TEXT")
    private String goalName;

    //저축 목표 금액
    @Column
    private Long goalPrice;
    //저축 현재 금액
    @Column
    private Long currentPrice;

    //저축 목표 유형
    @Column
    private String goalType;

    //시작 날짜
    @Column
    private LocalDate startDate;

    //끝나는 날짜
    @Column
    private LocalDate endDate;

    //수정한 날짜
    @Column
    private LocalDateTime updateDate;

    public void updateCurrentPrice(Long savingPrice) {
        this.currentPrice += savingPrice;
    }
}
