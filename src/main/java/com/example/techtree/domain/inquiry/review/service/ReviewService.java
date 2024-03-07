package com.example.techtree.domain.inquiry.review.service;

import com.example.techtree.domain.inquiry.review.dao.ReviewRepository;
import com.example.techtree.domain.inquiry.review.dto.ReviewDto;
import com.example.techtree.domain.inquiry.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Page<Review> getList(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return this.reviewRepository.findAll(pageable);
    }

    public void create(ReviewDto reviewDto) {
        Review saveReview = reviewDto.toEntity();
        this.reviewRepository.save(saveReview);
    }
}
