package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.ProductCreationRequest;
import com.iuh.kidclothes.dto.request.ProductUpdateRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.ProductRespone;
import com.iuh.kidclothes.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    // 1. Thêm sản phẩm (Chỉ Nhân Viên)
    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<ProductRespone>> createProduct(
            @RequestBody @Valid ProductCreationRequest request) {
        ProductRespone result = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespone.<ProductRespone>builder().result(result).build());
    }

    // 2. Cập nhật sản phẩm (Chỉ Nhân Viên)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<ProductRespone>> updateProduct(
            @PathVariable String id,
            @RequestBody @Valid ProductUpdateRequest request) {
        ProductRespone result = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiRespone.<ProductRespone>builder().result(result).build());
    }

    // 3. Xóa sản phẩm (Chỉ Nhân Viên)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiRespone.<Void>builder()
                .message("Xóa sản phẩm thành công")
                .build());
    }

    // 4. Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<ApiRespone<List<ProductRespone>>> getAllProducts() {
        return ResponseEntity.ok(ApiRespone.<List<ProductRespone>>builder()
                .result(productService.getAllProducts())
                .build());
    }

    // 5. Xem chi tiết sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespone<ProductRespone>> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(ApiRespone.<ProductRespone>builder()
                .result(productService.getProductById(id))
                .build());
    }

    // 6. Tìm kiếm sản phẩm
    @GetMapping("/search")
    public ResponseEntity<ApiRespone<List<ProductRespone>>> searchProducts(
            @RequestParam String keyword) {
        return ResponseEntity.ok(ApiRespone.<List<ProductRespone>>builder()
                .result(productService.searchProducts(keyword))
                .build());
    }

    // 7. Xem sản phẩm theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiRespone<List<ProductRespone>>> getProductsByCategory(
            @PathVariable String categoryId) {
        return ResponseEntity.ok(ApiRespone.<List<ProductRespone>>builder()
                .result(productService.getProductsByCategory(categoryId))
                .build());
    }

    // 8. Lọc sản phẩm theo giá
    @GetMapping("/filter")
    public ResponseEntity<ApiRespone<List<ProductRespone>>> filterByPrice(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(ApiRespone.<List<ProductRespone>>builder()
                .result(productService.filterProductsByPrice(minPrice, maxPrice))
                .build());
    }

    // 9. Xem sản phẩm bán chạy
    @GetMapping("/trending")
    public ResponseEntity<ApiRespone<List<ProductRespone>>> getTrendingProducts(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "10") int limit) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        return ResponseEntity.ok(ApiRespone.<List<ProductRespone>>builder()
                .result(productService.getTrendingProducts(start, end, limit))
                .build());
    }
}