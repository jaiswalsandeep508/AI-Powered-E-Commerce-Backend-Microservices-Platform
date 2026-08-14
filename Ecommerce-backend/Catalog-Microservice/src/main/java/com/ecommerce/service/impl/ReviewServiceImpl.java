package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public ReviewResponse addReview(Long productId, ReviewRequest request) {
        return null;
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {
        return List.of();
    }
}
