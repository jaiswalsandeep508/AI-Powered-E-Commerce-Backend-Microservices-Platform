package com.ecommerce.service.impl;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.dto.response.BrandResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.mapper.BrandMapper;
import com.ecommerce.model.Brand;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.BrandReferenceService;
import com.ecommerce.service.BrandService;
import com.ecommerce.service.factory.BrandFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandFactory brandFactory;

    private final BrandMapper brandMapper;

    private final BrandReferenceService brandReferenceService;


// ************************ Create Brand ************************
    @Override
    public BrandResponse createBrand(BrandRequest request) {

        Brand brand = brandFactory.create(request);

        Long userId = UserContext.getCurrentUserId();

        brand.setCreatedBy(userId);
        brand.setUpdatedBy(userId);

        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(savedBrand);
    }


// ************************ Update Brand ************************
    @Override
    public BrandResponse updateBrand(Long brandId,BrandRequest request) {

        Brand brand = brandFactory.getById(brandId);

        brandFactory.validateBrandNameForUpdate(brandId,request.getName());

        brandMapper.updateFromRequest(request,brand);

        brand.setUpdatedBy(UserContext.getCurrentUserId());

        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toResponse(savedBrand);
    }


// ************************ Delete Brand ************************
    @Override
    public void deleteBrand(Long brandId) {

        Brand brand =
                brandFactory.getById(brandId);

        if (brandReferenceService.isBrandInUse(brandId)) {
            throw new BadRequestException("Brand cannot be deleted because it is associated with one or more products.");
        }

        brandRepository.delete(brand);
    }


// ************************ Get Brand By ID ************************
    @Override
    public BrandResponse getBrandById(Long brandId) {

        Brand brand = brandFactory.getById(brandId);

        return brandMapper.toResponse(brand);
    }


// ************************ Get All Brands ************************
    @Override
    public List<BrandResponse> getAllBrands() {

        return brandRepository
                .findAll()
                .stream()
                .map(brandMapper::toResponse)
                .toList();
    }

}