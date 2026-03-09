package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.CategoryCreationRequest;
import com.iuh.kidclothes.dto.request.CategoryUpdateRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.CategoryRespone;
import com.iuh.kidclothes.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    // 1. Xem danh sách danh mục (Public)
    @GetMapping
    public ResponseEntity<ApiRespone<List<CategoryRespone>>> getAllCategories() {
        return ResponseEntity.ok(ApiRespone.<List<CategoryRespone>>builder()
                .result(categoryService.getAllCategories())
                .build());
    }

    // 2. Xem chi tiết danh mục (Public)
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespone<CategoryRespone>> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(ApiRespone.<CategoryRespone>builder()
                .result(categoryService.getCategoryById(id))
                .build());
    }

    // 3. Thêm danh mục (Chỉ STAFF)
    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<CategoryRespone>> createCategory(
            @RequestBody @Valid CategoryCreationRequest request) {
        CategoryRespone result = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespone.<CategoryRespone>builder().result(result).build());
    }

    // 4. Cập nhật danh mục (Chỉ STAFF)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<CategoryRespone>> updateCategory(
            @PathVariable String id,
            @RequestBody @Valid CategoryUpdateRequest request) {
        CategoryRespone result = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(ApiRespone.<CategoryRespone>builder().result(result).build());
    }

    // 5. Xóa danh mục (Chỉ STAFF)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<Void>> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiRespone.<Void>builder()
                .message("Xóa danh mục thành công")
                .build());
    }
}

