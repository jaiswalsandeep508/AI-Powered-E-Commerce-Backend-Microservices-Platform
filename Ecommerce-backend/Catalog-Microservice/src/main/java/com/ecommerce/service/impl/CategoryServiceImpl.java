package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return null;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return List.of();
    }
}
