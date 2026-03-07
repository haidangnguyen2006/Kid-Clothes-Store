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
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String categoryId;
    private String name;
    private Double price;
    private String description;
    private List<String> images; // Lưu URL hình ảnh
    private List<ProductVariant> variants;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ProductVariant {
    private String size;
    private String color;
    private Integer stock; // Tồn kho cho riêng size/màu này
}