package com.ecommerce.service.impl;

import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.BrandReferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandReferenceServiceImpl implements BrandReferenceService {

    private final ProductRepository productRepository;

// ************************ Check Brand Reference ************************
    @Override
    public boolean isBrandInUse(Long brandId) {
        log.info("Checking whether brand with ID: {} is in use.", brandId);
        return productRepository
                .existsByBrandBrandId(brandId);
    }

}