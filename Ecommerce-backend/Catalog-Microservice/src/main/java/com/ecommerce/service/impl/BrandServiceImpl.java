package com.ecommerce.service.impl;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.dto.response.BrandResponse;
import com.ecommerce.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Override
    public BrandResponse createBrand(BrandRequest request) {
        return null;
    }

    @Override
    public BrandResponse updateBrand(Long brandId, BrandRequest request) {
        return null;
    }

    @Override
    public void deleteBrand(Long brandId) {

    }

    @Override
    public BrandResponse getBrandById(Long brandId) {
        return null;
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return List.of();
    }
}
