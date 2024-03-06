package com.example.techtree.domain.inquiry.review.controller;

import com.example.techtree.domain.inquiry.review.entity.Review;
import com.example.techtree.domain.inquiry.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/list")
    public String list(Model model) {
        List<Review> reviewList = this.reviewService.getList();
        model.addAttribute("reviewList", reviewList);
        return "domain/review/review_list";
    }
}
