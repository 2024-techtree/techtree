package com.example.techtree.domain.inquiry.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
}
