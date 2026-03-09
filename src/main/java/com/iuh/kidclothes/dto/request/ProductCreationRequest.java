package com.iuh.kidclothes.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.iuh.kidclothes.dto.ProductVariantDTO;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    String name;

    @NotBlank(message = "Danh mục không được để trống")
    String categoryId;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    Double price;

    String description;

    List<String> images; // Danh sách URL hình ảnh

    @Valid
    List<ProductVariantDTO> variants; // Danh sách size, màu và tồn kho
}