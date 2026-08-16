package com.ecommerce.service.impl;

import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.BrandReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandReferenceServiceImpl implements BrandReferenceService {

    private final ProductRepository productRepository;

// ************************ Check Brand Reference ************************
    @Override
    public boolean isBrandInUse(Long brandId) {
        return productRepository
                .existsByBrandBrandId(brandId);
    }

}