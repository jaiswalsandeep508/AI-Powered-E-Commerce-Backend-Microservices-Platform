package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.factory.CategoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryFactory categoryFactory;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // ************************ Create Category ************************
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryFactory.createCategory(request);
        Long userId = UserContext.getCurrentUserId();
        category.setCreatedBy(userId);
        category.setUpdatedBy(userId);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(
                savedCategory
        );
    }


    // ************************ Update Category ************************
    @Override
    public CategoryResponse updateCategory(Long categoryId,CategoryRequest request) {

        Category category = categoryFactory.getCategoryById(categoryId);

        categoryFactory.validateCategoryNameForUpdate(categoryId,request.getName());

        categoryMapper.updateFromRequest(request,category);

        /*
         * Update the parent category.
         *
         * If parentCategoryId is null,
         * the category becomes a root category.
         */

        if (request.getParentCategoryId() != null) {
            Category parentCategory = categoryFactory.getCategoryById(request.getParentCategoryId());
            categoryFactory.validateParentCategory(category,parentCategory);
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        category.setUpdatedBy(UserContext.getCurrentUserId());
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    // ************************ Delete Category ************************
    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryFactory.getCategoryById(categoryId);

        boolean hasChildCategories = categoryRepository.existsByParentCategoryCategoryId(categoryId);

        if (hasChildCategories) {
            throw new BadRequestException("Cannot delete category because child categories are referring to this parent category.");
        }

        categoryRepository.delete(category);
    }

    // ************************ Get Category By ID ************************
    @Override
    public CategoryResponse getCategoryById(Long categoryId) {

        Category category = categoryFactory.getCategoryById(categoryId);

        return categoryMapper.toResponse(category);
    }


    // ************************ Get All Categories ************************
    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
