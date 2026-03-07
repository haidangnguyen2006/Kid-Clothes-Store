package com.iuh.kidclothes.entity;

import com.iuh.kidclothes.enums.OrderStatus;
import com.iuh.kidclothes.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    String id;
    String userId;

    // snapshot để không bị mất khi User đổi địa chỉ
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class OrderItem {
    private String productId;
    private String productName; // snapshot tên SP tại thời điểm mua
    private String size;
    private String color;
    private Integer quantity;
    private Double price; // snapshot giá tại thời điểm mua
}
