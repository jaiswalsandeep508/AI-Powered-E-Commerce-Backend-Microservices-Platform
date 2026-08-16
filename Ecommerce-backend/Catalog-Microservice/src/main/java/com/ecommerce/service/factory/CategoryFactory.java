package com.ecommerce.service.factory;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryRepository categoryRepository;


// ************************ Create Category ************************

    public Category createCategory(
            CategoryRequest request
    ) {

        validateDuplicateCategoryName(
                request.getName()
        );

        Category parentCategory = null;

        if (request.getParentCategoryId() != null) {

            parentCategory = getCategoryById(
                    request.getParentCategoryId()
            );
        }

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategory(parentCategory)
                .imageUrl(request.getImageUrl())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();
    }


// ************************ Get Category ************************

    public Category getCategoryById(
            Long categoryId
    ) {

        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "categoryId",
                                categoryId
                        )
                );
    }


// ************************ Duplicate Name Validation ************************

    public void validateDuplicateCategoryName(
            String name
    ) {

        if (categoryRepository
                .existsByNameIgnoreCase(name)) {

            throw new ResourceAlreadyExistsException(
                    "Category",
                    "name",
                    name
            );
        }
    }


// ************************ Update Name Validation ************************

    public void validateCategoryNameForUpdate(
            Long categoryId,
            String name
    ) {

        categoryRepository
                .findByNameIgnoreCase(name)
                .ifPresent(category -> {

                    if (!category
                            .getCategoryId()
                            .equals(categoryId)) {

                        throw new ResourceAlreadyExistsException(
                                "Category",
                                "name",
                                name
                        );
                    }
                });
    }


// ************************ Parent Validation ************************

    public void validateParentCategory(
            Category category,
            Category parentCategory
    ) {

        // A category cannot be its own parent.

        if (category.getCategoryId()
                .equals(parentCategory.getCategoryId())) {

            throw new BadRequestException(
                    "Category cannot be its own parent."
            );
        }


        // A category cannot have one of its child
        // categories as its parent.

        Category currentCategory = parentCategory;

        while (currentCategory != null) {

            if (currentCategory
                    .getCategoryId()
                    .equals(category.getCategoryId())) {

                throw new BadRequestException(
                        "Category cannot be assigned to one of its child categories."
                );
            }

            currentCategory =
                    currentCategory.getParentCategory();
        }
    }

}