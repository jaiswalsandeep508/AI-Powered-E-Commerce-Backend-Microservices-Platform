package com.ecommerce.service.factory;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.BrandMapper;
import com.ecommerce.model.Brand;
import com.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandFactory {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public Brand create(BrandRequest request) {
        validateDuplicateBrandName(request.getName());

        return brandMapper.toEntity(request);
    }

    private void validateDuplicateBrandName(String name) {
        if(brandRepository.existsByNameIgnoreCase(name)){
            throw new ResourceAlreadyExistsException("Brand", "name", name);
        }
    }

    // ************************ Update Name Validation ************************

    public void validateBrandNameForUpdate(
            Long brandId,
            String name
    ) {

        brandRepository
                .findByNameIgnoreCase(name)
                .ifPresent(existingBrand -> {

                    if (!existingBrand
                            .getBrandId()
                            .equals(brandId)) {

                        throw new ResourceAlreadyExistsException(
                                "Brand",
                                "name",
                                name
                        );
                    }
                });
    }

    // ************************ Get Brand ************************

    public Brand getById(
            Long brandId
    ) {

        return brandRepository
                .findById(brandId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand",
                                "id",
                                brandId
                        )
                );
    }


}
