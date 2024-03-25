package com.example.techtree.domain.inquiry.review.service;

import com.example.techtree.domain.inquiry.review.dao.ReviewRepository;
import com.example.techtree.domain.inquiry.review.dto.ReviewDto;
import com.example.techtree.domain.inquiry.review.entity.Review;
import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    public Page<Review> getList(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return this.reviewRepository.findAll(pageable);
    }

    public void create(ReviewDto reviewDto) {
        Review saveReview = reviewDto.toEntity();
        this.reviewRepository.save(saveReview);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member not found with username: " + username));
    }

    @Transactional
    public void like(Review review, Member member) {
        // 이미 추천한 경우에는 중복 추천을 막기 위해 추가 로직을 작성해야 합니다.
        if (review.getLike().contains(member)) {
            throw new RuntimeException("You have already liked this review");
        }

        // 추천 처리
        review.getLike().add(member);
        reviewRepository.save(review);
    }

//    public void like(Long reviewId, Member member) {
//        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
//        if (optionalReview.isPresent()) {
//            Review review = optionalReview.get();
//            review.getLike().add(member);
//            reviewRepository.save(review);
//        } else {
//            throw new RuntimeException("Review not found with ID: " + reviewId);
//        }
//    }
}
