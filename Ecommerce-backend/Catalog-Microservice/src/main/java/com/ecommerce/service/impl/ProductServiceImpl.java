package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return null;
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(Integer page, Integer size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public PageResponse<ProductResponse> searchProducts(String keyword, Integer page, Integer size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public PageResponse<ProductResponse> filterProducts(ProductFilterRequest request, Integer page, Integer size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public ProductImageResponse uploadProductImage(Long productId, ProductImageRequest request) {
        return null;
    }

    @Override
    public void deleteProductImage(Long imageId) {

    }
}
