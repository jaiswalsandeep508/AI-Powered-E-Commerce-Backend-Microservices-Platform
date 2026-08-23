package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.mapper.ProductImageMapper;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.factory.ProductFactory;
import com.ecommerce.service.factory.ProductImageFactory;
import com.ecommerce.specification.ProductSpecification;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductFactory productFactory;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductImageFactory productImageFactory;
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    // ************************ Create Product ************************
    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = productFactory.create(request);

        Long userId = UserContext.getCurrentUserId();

        product.setSellerId(userId);
        product.setCreatedBy(userId);
        product.setUpdatedBy(userId);

        product.setSpecialPrice(request.getPrice().subtract(request.getDiscount()));

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    // ************************ Update Product ************************
    @Override
    public ProductResponse updateProduct(Long productId,ProductRequest request) {

        Product product = productFactory.getProductById(productId);

        productFactory.validateProductRequest(request);

        productFactory.validateSkuForUpdate(productId,request.getSku());

        productMapper.updateFromRequest(request,product);

        product.setCategory(productFactory.getCategoryById(request.getCategoryId()));

        product.setBrand(productFactory.getBrandById(request.getBrandId()));

        product.setSpecialPrice(request.getPrice().subtract(request.getDiscount()));

        product.setUpdatedBy(UserContext.getCurrentUserId());

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    // ************************ Delete Product ************************
    @Override
    public void deleteProduct(Long productId) {
        Product product = productFactory.getProductById(productId);
        productRepository.delete(product);
    }

    // ************************ Get Product By ID ************************
    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productFactory.getProductById(productId);
        return productMapper.toResponse(product);
    }

    // ************************ Get All Products ************************
    @Override
    public PageResponse<ProductResponse> getAllProducts(Integer page,Integer size,String sortBy,String sortDir) {

        Page<ProductResponse> result = productRepository
                .findAll(PageRequestUtil.createPageRequest(page,size,sortBy,sortDir))
                .map(productMapper::toResponse);
        return PageResponseUtil.from(result);
    }

    // ************************ Search Products ************************
    @Override
    public PageResponse<ProductResponse> searchProducts(String keyword,Integer page,Integer size,String sortBy,String sortDir) {

        Page<ProductResponse> result = productRepository
                .findByNameContainingIgnoreCase(keyword,PageRequestUtil.createPageRequest(page,size,sortBy,sortDir))
                .map(productMapper::toResponse);

        return PageResponseUtil.from(result);
    }

    // ************************ Filter Products ************************
    @Override
    public PageResponse<ProductResponse> filterProducts(ProductFilterRequest request,Integer page,Integer size,String sortBy,String sortDir) {

        Page<ProductResponse> result = productRepository
                .findAll(ProductSpecification.filter(request),PageRequestUtil.createPageRequest(page,size,sortBy,sortDir))
                .map(productMapper::toResponse);

        return PageResponseUtil.from(result);
    }

    @Override
    public List<ProductResponse> sortProducts(String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        List<ProductResponse> responses = productRepository.findAll(sort)
                .stream()
                .map(productMapper::toResponse)
                .toList();

        return responses;
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::toResponse);
    }

    @Override
    public ProductImageResponse uploadProductImage(Long productId, ProductImageRequest request) {
        ProductImage productImage =
                productImageFactory.create(productId, request);
        Long userId = UserContext.getCurrentUserId();
        productImage.setCreatedBy(userId);
        productImage.setUpdatedBy(userId);

        return productImageMapper.toResponse(
                productImageRepository.save(productImage));
    }

    @Override
    public void deleteProductImage(Long imageId) {
        ProductImage productImage =
                productImageFactory.getProductImageById(imageId);
        productImageRepository.delete(productImage);
    }

}
