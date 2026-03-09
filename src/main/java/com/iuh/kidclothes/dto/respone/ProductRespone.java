package com.iuh.kidclothes.dto.respone;

import com.iuh.kidclothes.dto.ProductVariantDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRespone {
    String id;
    String categoryId;
    String name;
    Double price;
    String description;
    List<String> images;
    List<ProductVariantDTO> variants;
}