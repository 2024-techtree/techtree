package com.example.techtree.domain.inquiry.review.controller;

import com.example.techtree.domain.inquiry.review.dto.ReviewDto;
import com.example.techtree.domain.inquiry.review.entity.Review;
import com.example.techtree.domain.inquiry.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Review> reviewList = this.reviewService.getList();
        model.addAttribute("reviewList", reviewList);
        return "domain/review/review_list";
    }

    @GetMapping("/create")
    public String getReviewCreate(ReviewDto reviewDto, Model model) {
        model.addAttribute(reviewDto);
        return "domain/review/review_create";
    }

    @PostMapping("/create")
    public String postReviewCreate(@Valid ReviewDto reviewDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/review/review_create";
        }
        this.reviewService.create(reviewDto);
        return "redirect:/review/list";
    }
}
