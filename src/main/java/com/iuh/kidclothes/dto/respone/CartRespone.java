package com.iuh.kidclothes.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRespone {
    String id;
    String userId;
    List<CartItemRespone> items;
    Double totalAmount; // Tổng tiền tạm tính của giỏ hàng
}

