package com.example.techtree.domain.inquiry.review.entity;

import com.example.techtree.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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

	private LocalDate createDate;
	private LocalDate modifyDate;

	@ManyToMany
	Set<Member> like;

	private Integer likeCount;
}
