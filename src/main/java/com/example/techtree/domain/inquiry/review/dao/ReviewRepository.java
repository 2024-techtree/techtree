package com.example.techtree.domain.inquiry.review.dao;

import com.example.techtree.domain.inquiry.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Pageable pageable);

    List<Review> findTop3ByOrderByLikeCountDesc();

    long count();
}
