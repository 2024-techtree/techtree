package com.example.techtree.domain.inquiry.review.service;

import com.example.techtree.domain.inquiry.review.dao.ReviewRepository;
import com.example.techtree.domain.inquiry.review.dto.ReviewDto;
import com.example.techtree.domain.inquiry.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getList() {
        return this.reviewRepository.findAll();
    }

    public void create(ReviewDto reviewDto) {
        Review saveReview = reviewDto.toEntity();
        this.reviewRepository.save(saveReview);
    }
}
