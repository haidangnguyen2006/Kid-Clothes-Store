package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.request.CategoryCreationRequest;
import com.iuh.kidclothes.dto.request.CategoryUpdateRequest;
import com.iuh.kidclothes.dto.respone.CategoryRespone;
import com.iuh.kidclothes.entity.Category;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.mapper.CategoryMapper;
import com.iuh.kidclothes.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public List<CategoryRespone> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryResponeList(categories);
    }

    public CategoryRespone getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        return categoryMapper.toCategoryRespone(category);
    }

    public CategoryRespone createCategory(CategoryCreationRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .decription(request.getDescription())
                .build();

        category = categoryRepository.save(category);
        log.info("Danh mục '{}' đã được tạo", request.getName());
        return categoryMapper.toCategoryRespone(category);
    }

    public CategoryRespone updateCategory(String id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));

        if (request.getName() != null && !request.getName().isBlank()) {
            category.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            category.setDecription(request.getDescription());
        }

        category = categoryRepository.save(category);
        log.info("Danh mục '{}' đã được cập nhật", id);
        return categoryMapper.toCategoryRespone(category);
    }

    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Danh mục không tồn tại");
        }

        categoryRepository.deleteById(id);
        log.info("Danh mục '{}' đã được xóa", id);
    }
}

