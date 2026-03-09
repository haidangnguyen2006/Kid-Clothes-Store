package com.iuh.kidclothes.dto.respone;

import com.iuh.kidclothes.entity.OrderItem;

import com.iuh.kidclothes.enums.OrderStatus;
import com.iuh.kidclothes.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRespone {
    String id;
    String userId;
    String receiverName;
    String receiverPhone;
    String shippingAddress;
    List<OrderItem> items;
    Double totalAmount;
    Double shippingFee;
    PaymentMethod paymentMethod;
    OrderStatus status;
    LocalDateTime createdAt;
}
