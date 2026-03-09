package com.iuh.kidclothes.dto.request;

import com.iuh.kidclothes.dto.ProductVariantDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    String name;
    String categoryId;

    @Positive(message = "Giá phải lớn hơn 0")
    Double price;

    String description;
    List<String> images;

    @Valid
    List<ProductVariantDTO> variants; // Nhân viên dùng cái này để cập nhật tồn kho
}