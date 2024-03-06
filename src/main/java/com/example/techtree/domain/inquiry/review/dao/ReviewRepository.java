package com.example.techtree.domain.inquiry.review.dao;

import com.example.techtree.domain.inquiry.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
