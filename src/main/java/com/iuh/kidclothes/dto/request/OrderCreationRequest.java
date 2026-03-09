package com.iuh.kidclothes.dto.request;

import com.iuh.kidclothes.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    @NotBlank(message = "Tên người nhận không được để trống")
    String receiverName;

    @NotBlank(message = "Số điện thoại không được để trống")
    String receiverPhone;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    String shippingAddress;

    @NotNull(message = "Vui lòng chọn phương thức thanh toán")
    PaymentMethod paymentMethod;
}