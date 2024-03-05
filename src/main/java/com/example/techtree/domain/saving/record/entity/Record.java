package com.example.techtree.domain.saving.record.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingWrite_id;

    // 목표 이름
    @Column
    private String goalName;

    // 저축할 금액
    @Column
    private Long savingPrice;

    //목표 유형
    @Column
    private String goalType;

    //저축날짜
    @Column
    private LocalDate savingDate;
}
