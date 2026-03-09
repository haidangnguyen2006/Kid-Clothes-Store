package com.iuh.kidclothes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantDTO {
    @NotBlank(message = "Size không được để trống")
    String size;

    @NotBlank(message = "Màu sắc không được để trống")
    String color;

    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    Integer stock;
}