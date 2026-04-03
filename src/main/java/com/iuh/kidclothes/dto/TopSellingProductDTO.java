package com.iuh.kidclothes.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // Auto generate setter & getter
@NoArgsConstructor // generate constructor with no Args
@AllArgsConstructor // generate constrctor with all Args
@Builder
@FieldDefaults(
        level = AccessLevel.PRIVATE)
public class TopSellingProductDTO {
    String productId;
    String productName;
    Integer totalQuantitySold;
}
