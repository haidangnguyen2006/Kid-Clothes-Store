package com.iuh.kidclothes.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRespone {
    String productId;
    String productName;
    String imageUrl;
    String size;
    String color;
    Integer quantity;
    Double price;
    Double subTotal; // quantity * price
}
