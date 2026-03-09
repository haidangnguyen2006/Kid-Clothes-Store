package com.iuh.kidclothes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private String productId;
    private String productName; // snapshot tên SP tại thời điểm mua
    private String size;
    private String color;
    private Integer quantity;
    private Double price; // snapshot giá tại thời điểm mua
}

