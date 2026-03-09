package com.iuh.kidclothes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    private String size;
    private String color;
    private Integer stock; // Tồn kho cho riêng size/màu này
}
