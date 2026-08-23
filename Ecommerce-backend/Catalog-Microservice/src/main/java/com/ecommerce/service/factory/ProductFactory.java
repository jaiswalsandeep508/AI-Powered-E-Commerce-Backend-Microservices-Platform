package com.ecommerce.service.factory;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;


// ************************ Create Product ************************

    public Product create(ProductRequest request) {

        validateProductRequest(request);
        validateDuplicateSku(request.getSku());

        Product product = productMapper.toEntity(request);

        Category category = getCategoryById(request.getCategoryId());

        Brand brand = getBrandById(request.getBrandId());

        product.setCategory(category);
        product.setBrand(brand);

        return product;
    }


// ************************ Get Product ************************

    public Product getProductById(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "id",
                                productId
                        )
                );
    }


// ************************ Get Category ************************

    public Category getCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "id",
                                categoryId
                        )
                );
    }


// ************************ Get Brand ************************

    public Brand getBrandById(Long brandId) {

        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand",
                                "id",
                                brandId
                        )
                );
    }


// ************************ SKU Validation ************************
    public void validateDuplicateSku(String sku) {
        if (productRepository.existsBySkuIgnoreCase(sku)) {
            throw new ResourceAlreadyExistsException("Product","sku",sku);
        }
    }


// ************************ Update SKU Validation ************************

    public void validateSkuForUpdate(
            Long productId,
            String sku
    ) {

        productRepository.findBySkuIgnoreCase(sku).ifPresent(existingProduct -> {
                    if (!existingProduct.getProductId().equals(productId)) {
                        throw new ResourceAlreadyExistsException("Product","sku",sku);
                    }
                });
    }


// ************************ Product Validation ************************

    public void validateProductRequest(ProductRequest request) {

        BigDecimal price = request.getPrice();

        BigDecimal discount = request.getDiscount();


        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Product price must be greater than zero.");
        }

        if (discount == null) {
            throw new BadRequestException("Product discount cannot be null.");
        }

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Product discount cannot be negative.");
        }

        if (discount.compareTo(price) > 0) {
            throw new BadRequestException("Product discount cannot be greater than the product price.");
        }
    }

}