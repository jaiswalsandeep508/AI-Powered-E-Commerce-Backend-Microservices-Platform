package com.ecommerce.service.impl;

import com.ecommerce.service.BrandReferenceService;
import org.springframework.stereotype.Service;

@Service
public class BrandReferenceServiceImpl implements BrandReferenceService {
    @Override
    public boolean isBrandInUse(Long brandId) {
        return false;
    }
}
