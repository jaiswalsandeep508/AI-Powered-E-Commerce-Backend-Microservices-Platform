package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.mapper.ReviewMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.service.ReviewService;
import com.ecommerce.service.factory.ReviewFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewFactory reviewFactory;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;

    @Override
    public ReviewResponse addReview(Long productId, ReviewRequest request) {
        Product product = reviewFactory.getProduct(productId);
        reviewFactory.validateDuplicateReview(
                product,
                request.getUserId()
        );
        Review review = reviewFactory.create(request);
        review.setProduct(product);
        Review savedReview = reviewRepository.save(review);

        updateProductRating(product);

        return reviewMapper.toResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewFactory.getReview(reviewId);
        reviewMapper.updateFromRequest(request, review);
        Review updatedReview = reviewRepository.save(review);

        updateProductRating(review.getProduct());

        return reviewMapper.toResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewFactory.getReview(reviewId);
        Product product = review.getProduct();
        reviewRepository.delete(review);

        updateProductRating(product);

    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {
        Product product = reviewFactory.getProduct(productId);
        List<Review> reviews = reviewRepository.findByProduct(product);

        return reviews.stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    // ************************ Helper Method ********************************
    private void updateProductRating(Product product) {
        List<Review> reviews = reviewRepository.findByProduct(product);
        int reviewCount = reviews.size();
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        product.setReviewCount(reviewCount);
        product.setAverageRating(averageRating);
        productRepository.save(product);
    }
}
