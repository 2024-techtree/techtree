package com.example.techtree.domain.inquiry.review.dto;

import com.example.techtree.domain.inquiry.review.entity.Review;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDto {
    @Size(
            min = 5, max = 15, message = "최소 5글자, 최대 20글자 입력해주세요."
    )
    private String title;

    @Size(
            min = 20, max = 200, message = "최소 20글자, 최대 200글자 입력해주세요."
    )
    private String content;

    public Review toEntity() {
        return Review.builder()
                .title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();

    }
}
