package com.example.techtree.domain.inquiry.review.controller;

import com.example.techtree.domain.inquiry.review.dto.ReviewDto;
import com.example.techtree.domain.inquiry.review.entity.Review;
import com.example.techtree.domain.inquiry.review.service.ReviewService;
import com.example.techtree.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "review", defaultValue = "0") int page) {
        Page<Review> reviewList = this.reviewService.getList(page);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String like(Principal principal, @PathVariable("id") Long id) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = principal.getName();

        // 해당 후기 정보 가져오기
        Review review = reviewService.getReviewById(id);

        // 현재 로그인한 사용자를 Member로 변환하여 추천 처리
        Member member = reviewService.getMemberByUsername(username);

        // 추천 처리
        reviewService.like(review, member);

        // 추천 처리 후 해당 후기의 상세 페이지로 리다이렉트
        return String.format("redirect:/review/list");
    }
}
