package com.iuh.kidclothes.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document(collection = "carts")
public class Cart {
    @Id
    String id;
    String userId; // Reference to users collection
    List<CartItem> items;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CartItem {
    String productId;
    String size;
    String color;
    Integer quantity;
}