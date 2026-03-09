package com.iuh.kidclothes.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    @NotBlank(message = "ID sản phẩm không được để trống")
    String productId;

    @NotBlank(message = "Size không được để trống")
    String size;

    @NotBlank(message = "Màu sắc không được để trống")
    String color;

    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    int quantity;
}