package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.TopSellingProductDTO;
import com.iuh.kidclothes.dto.request.ProductCreationRequest;
import com.iuh.kidclothes.dto.request.ProductUpdateRequest;
import com.iuh.kidclothes.dto.respone.ProductRespone;
import com.iuh.kidclothes.entity.Product;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.mapper.ProductMapper;
import com.iuh.kidclothes.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    // =====================================
    // CHỨC NĂNG DÀNH CHO NHÂN VIÊN
    // =====================================

    @PreAuthorize("hasRole('STAFF')")
    public ProductRespone createProduct(ProductCreationRequest request) {
        // Thêm sản phẩm mới [cite: 75, 129]
        Product product = productMapper.toProduct(request);
        product = productRepository.save(product);
        return productMapper.toProductRespone(product);
    }

    @PreAuthorize("hasRole('STAFF')")
    public ProductRespone updateProduct(String productId, ProductUpdateRequest request) {
        // Cập nhật thông tin và tồn kho [cite: 76, 77, 130]
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        productMapper.updateProduct(product, request);
        product = productRepository.save(product);
        return productMapper.toProductRespone(product);
    }

    @PreAuthorize("hasRole('STAFF')")
    public void deleteProduct(String productId) {
        // Xóa sản phẩm [cite: 78, 169]
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        productRepository.deleteById(productId);
        log.info("Sản phẩm {} đã bị xóa", productId);
    }
    /**
     * Giảm số lượng tồn kho của một sản phẩm theo size và màu sắc cụ thể
     */
    public void reduceStock(String productId, String size, String color, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        if (product.getVariants() == null || product.getVariants().isEmpty()) {
            throw new RuntimeException("Sản phẩm chưa có thông tin tồn kho");
        }

        boolean variantFound = false;

        for (var variant : product.getVariants()) {
            if (variant.getSize().equals(size) && variant.getColor().equals(color)) {
                variantFound = true;

                if (variant.getStock() < quantity) {
                    throw new RuntimeException(
                            String.format("Sản phẩm %s (Size: %s, Màu: %s) không đủ số lượng. Còn lại: %d",
                                    product.getName(), size, color, variant.getStock())
                    );
                }

                // Trừ tồn kho
                variant.setStock(variant.getStock() - quantity);
                break;
            }
        }

        if (!variantFound) {
            throw new RuntimeException(String.format("Không tìm thấy phân loại Size: %s, Màu: %s cho sản phẩm này", size, color));
        }

        // Lưu lại thông tin sản phẩm đã cập nhật tồn kho
        productRepository.save(product);
    }
    // =====================================
    // CHỨC NĂNG CÔNG KHAI (KHÁCH HÀNG/GUEST)
    // =====================================

    public List<ProductRespone> getAllProducts() {
        // Xem danh sách sản phẩm [cite: 58, 102]
        List<Product> products = productRepository.findAll();
        return productMapper.toProductResponeList(products);
    }

    public ProductRespone getProductById(String productId) {
        // Xem chi tiết sản phẩm [cite: 60, 61]
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        return productMapper.toProductRespone(product);
    }

    public List<ProductRespone> searchProducts(String keyword) {
        // Tìm kiếm sản phẩm theo từ khóa [cite: 39, 59]
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return productMapper.toProductResponeList(products);
    }

    public List<ProductRespone> getProductsByCategory(String categoryId) {
        // Xem sản phẩm theo danh mục
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toProductResponeList(products);
    }

    public List<ProductRespone> filterProductsByPrice(Double minPrice, Double maxPrice) {
        // Lọc sản phẩm theo giá
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return productMapper.toProductResponeList(products);
    }

    public List<ProductRespone> getTrendingProducts(int limit) {
        // Xem sản phẩm bán chạy (sắp xếp theo số lượng đã bán)
        List<TopSellingProductDTO> products = productRepository.findTopSellingProducts(limit);
        return productMapper.toProductResponeTop(products);
    }
}