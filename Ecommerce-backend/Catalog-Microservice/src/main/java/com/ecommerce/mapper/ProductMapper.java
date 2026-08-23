package com.ecommerce.mapper;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .sku(request.getSku())
                .price(request.getPrice())
                .discount(request.getDiscount())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }


    public ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public void updateFromRequest(ProductRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
    }
}